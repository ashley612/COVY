from pathlib import Path
from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parent
SRC = ROOT / "logo_source.png"
RES = ROOT / "app" / "src" / "main" / "res"

WHITE = (255, 255, 255, 255)

img = Image.open(SRC).convert("RGBA")
print("source", img.size, img.mode)
px = img.load()
w, h = img.size
for p in [(0, 0), (w - 1, 0), (0, h - 1), (w - 1, h - 1), (w // 2, h // 2)]:
    print("pixel", p, px[p])
print("bbox", img.getbbox())


def compose_square(source: Image.Image, size: int, content_ratio: float = 1.0) -> Image.Image:
    canvas = Image.new("RGBA", (size, size), WHITE)
    inner = max(1, int(size * content_ratio))
    fitted = source.copy()
    fitted.thumbnail((inner, inner), Image.Resampling.LANCZOS)
    x = (size - fitted.width) // 2
    y = (size - fitted.height) // 2
    canvas.paste(fitted, (x, y), fitted)
    return canvas


def make_round(square: Image.Image) -> Image.Image:
    size = square.size[0]
    mask = Image.new("L", (size, size), 0)
    ImageDraw.Draw(mask).ellipse((0, 0, size - 1, size - 1), fill=255)
    out = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    out.paste(square, (0, 0))
    out.putalpha(mask)
    return out


def make_monochrome(source: Image.Image, size: int) -> Image.Image:
    rgba = compose_square(source, size, content_ratio=0.66)
    pixels = rgba.load()
    for y in range(size):
        for x in range(size):
            r, g, b, a = pixels[x, y]
            brightness = 0.299 * r + 0.587 * g + 0.114 * b
            # Drop near-white background; keep logo shapes as white silhouette
            if a < 20 or brightness > 245:
                pixels[x, y] = (255, 255, 255, 0)
            else:
                pixels[x, y] = (255, 255, 255, 255)
    return rgba


mipmap_sizes = {
    "mipmap-mdpi": 48,
    "mipmap-hdpi": 72,
    "mipmap-xhdpi": 96,
    "mipmap-xxhdpi": 144,
    "mipmap-xxxhdpi": 192,
}

for folder, size in mipmap_sizes.items():
    dest = RES / folder
    dest.mkdir(parents=True, exist_ok=True)
    square = compose_square(img, size, content_ratio=1.0)
    square.save(dest / "ic_launcher.png", optimize=True)
    make_round(square).save(dest / "ic_launcher_round.png", optimize=True)
    print("wrote", dest)

nodpi = RES / "drawable-nodpi"
nodpi.mkdir(parents=True, exist_ok=True)
compose_square(img, 432, content_ratio=0.66).save(
    nodpi / "ic_launcher_foreground.png", optimize=True
)
make_monochrome(img, 432).save(nodpi / "ic_launcher_monochrome.png", optimize=True)

drawable = RES / "drawable"
drawable.mkdir(parents=True, exist_ok=True)
compose_square(img, 512, content_ratio=1.0).save(drawable / "covy_logo.png", optimize=True)

print("done")
