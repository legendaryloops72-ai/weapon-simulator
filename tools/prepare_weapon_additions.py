#!/usr/bin/env python3
"""Prepare the 17 supplied weapon crops and keep their conservative labels.

The source crops include occasional neighboring weapon fragments at the edges.
Boxes below remove only those edge fragments; no semantic weapon model is
invented beyond what the supplied filenames and visible silhouette support.
"""
from pathlib import Path
from PIL import Image

ROOT = Path(__file__).resolve().parents[1]
SOURCE = Path('/home/ubuntu/weapon17_review')
OUT_DRAWABLE = ROOT / 'app/src/main/res/drawable'
OUT_ASSETS = ROOT / 'app/src/main/assets/images/weapons'
REVIEW = ROOT / 'docs/weapon17_crop_review.png'

items = [
    ('01_بندقية_هجومية_تكتيكية_بمخزون_ثابت.png', 'Tactical Fixed-Stock Rifle', (10, 0, 258, 161)),
    ('02_بندقية_هجومية_بنمط_M4.png', 'M4-Style Assault Rifle', (18, 0, 207, 161)),
    ('03_بندقية_بولببب_تكتيكية.png', 'Tactical Bullpup Rifle', (16, 0, 202, 161)),
    ('04_بندقية_هجومية_بمنظار_علوي.png', 'Top-Optic Assault Rifle', (28, 0, 268, 161)),
    ('05_بندقية_بولببب_خضراء_بمنظار.png', 'Green Scoped Bullpup Rifle', (0, 0, 250, 161)),
    ('06_بندقية_AK_بخشب.png', 'Wood AK Rifle', (0, 0, 280, 161)),
    ('07_بندقية_بولببب_بمنظار.png', 'Scoped Bullpup Rifle', (0, 0, 245, 166)),
    ('08_بندقية_هجومية_بمخزون_قابل_للطي.png', 'Folding-Stock Assault Rifle', (14, 0, 218, 166)),
    ('09_بندقية_هجومية_كلاسيكية.png', 'Classic Assault Rifle', (18, 0, 226, 166)),
    ('10_بندقية_هجومية_قصيرة.png', 'Compact Assault Rifle', (0, 0, 220, 166)),
    ('11_بندقية_آلية_بسبطانة_طويلة.png', 'Long-Barrel Automatic Rifle', (14, 0, 248, 166)),
    ('12_رشاش_خفيف_بحامل_ثنائي.png', 'Bipod Light Machine Gun', (0, 0, 270, 166)),
    ('13_بندقية_هجومية_بمنظار.png', 'Scoped Assault Rifle', (0, 0, 292, 190)),
    ('14_بندقية_آلية_بحامل_ثنائي.png', 'Bipod Automatic Rifle', (14, 0, 304, 190)),
    ('15_بندقية_خشبية_بسبطانة_مثقبة.png', 'Wood Rifle with Ported Barrel', (12, 0, 278, 190)),
    ('16_رشاش_خفيف_بمخزون_خشبي.png', 'Wood-Stock Light Machine Gun', (10, 0, 292, 190)),
    ('17_رشاش_آلي_بمخزون_قابل_للطي.png', 'Folding-Stock Machine Gun', (12, 0, 326, 190)),
]

for index, (filename, label, box) in enumerate(items, start=31):
    src = Image.open(SOURCE / filename).convert('RGB')
    crop = src.crop(box)
    out_name = f'weapon_{index:02d}'
    crop.save(OUT_DRAWABLE / f'{out_name}.webp', 'WEBP', quality=92, method=6)
    crop.save(OUT_ASSETS / f'{out_name}.webp', 'WEBP', quality=92, method=6)
    print(f'{out_name}\t{label}\t{box}\t{crop.size}')

# Deterministic visual review sheet without changing the shipped assets.
from PIL import ImageDraw, ImageFont
files = [OUT_DRAWABLE / f'weapon_{i:02d}.webp' for i in range(31, 48)]
font = ImageFont.load_default()
cell_w, cell_h, cols = 420, 250, 3
sheet = Image.new('RGB', (cols * cell_w, ((len(files)+cols-1)//cols) * cell_h), (22,24,32))
d = ImageDraw.Draw(sheet)
for i, path in enumerate(files):
    im = Image.open(path).convert('RGB')
    im.thumbnail((390, 190), Image.Resampling.LANCZOS)
    x = (i % cols) * cell_w + (cell_w - im.width)//2
    y = (i // cols) * cell_h + 12
    sheet.paste(im, (x, y))
    d.text(((i % cols)*cell_w + 12, (i//cols)*cell_h + 212), f'weapon_{i+31:02d}', fill='white', font=font)
sheet.save(REVIEW)
print(f'REVIEW\t{REVIEW}')
