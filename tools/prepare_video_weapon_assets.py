#!/usr/bin/env python3
from pathlib import Path
import subprocess
from PIL import Image

ROOT = Path(__file__).resolve().parents[1]
SHEET = Path('/home/ubuntu/weapon23-images/extracted/weapons_23_catalog.png')
VIDEO = Path('/home/ubuntu/upload/snaptik_7596426591916363039_v3.mp4')
ASSET_DIR = ROOT / 'app/src/main/assets/images/weapons'
DRAWABLE_DIR = ROOT / 'app/src/main/res/drawable'
RAW_DIR = ROOT / 'app/src/main/res/raw'

weapons = [
    ('glock', 'Glock', 'Pistols', 0, 0.0, 1.0),
    ('m1911', 'M1911', 'Pistols', 1, 2.0, 3.0),
    ('sig_p320', 'Sig P320', 'Pistols', 2, 3.0, 5.0),
    ('desert_eagle', 'Desert Eagle', 'Pistols', 3, 5.0, 7.0),
    ('beretta_m9', 'Beretta M9', 'Pistols', 4, 7.0, 8.0),
    ('makarov', 'Makarov', 'Pistols', 5, 9.0, 10.0),
    ('sw_model_19', 'S&W Model 19', 'Pistols', 6, 11.0, 12.0),
    ('browning_citori_725', 'Browning Citori 725', 'Shotguns', 7, 13.0, 15.0),
    ('remington_870', 'Remington 870', 'Shotguns', 8, 15.0, 17.0),
    ('m26_mass', 'M26 MASS', 'Shotguns', 9, 17.0, 19.0),
    ('dp12', 'DP-12', 'Shotguns', 10, 20.0, 22.0),
    ('origin_12', 'Origin 12', 'Shotguns', 11, 23.0, 25.0),
    ('aa12', 'AA-12', 'Shotguns', 12, 25.0, 28.0),
    ('mk14_ebr', 'Mk 14 EBR', 'Assault Rifles', 13, 28.0, 31.0),
    ('sks', 'SKS', 'Assault Rifles', 14, 31.0, 33.0),
    ('fn_fal', 'FN FAL', 'Assault Rifles', 15, 33.0, 35.0),
    ('dragunov', 'Dragunov', 'Snipers', 16, 35.0, 37.0),
    ('barrett', 'Barrett', 'Snipers', 17, 37.0, 39.0),
    ('marlin_model_336', 'Marlin Model 336', 'Snipers', 18, 39.0, 42.0),
    ('kar_98k', 'Kar 98k', 'Snipers', 19, 42.0, 46.0),
    ('r700', 'R700', 'Snipers', 20, 46.0, 50.0),
    ('hdr', 'HDR', 'Snipers', 21, 50.0, 55.0),
    ('ax50', 'AX50', 'Snipers', 22, 56.0, 61.0),
]

# The sheet is a 6-column grid for the first three rows and a 5-column grid for the last row.
im = Image.open(SHEET).convert('RGB')
cell_bounds = []
for row in range(3):
    for col in range(6):
        x0 = round(col * im.width / 6)
        x1 = round((col + 1) * im.width / 6)
        y0 = [0, 302, 539][row]
        y1 = [282, 519, 752][row]
        cell_bounds.append((x0, y0, x1, y1))
for col in range(5):
    x0 = round(col * im.width / 5)
    x1 = round((col + 1) * im.width / 5)
    cell_bounds.append((x0, 773, x1, 978))

ASSET_DIR.mkdir(parents=True, exist_ok=True)
DRAWABLE_DIR.mkdir(parents=True, exist_ok=True)
RAW_DIR.mkdir(parents=True, exist_ok=True)

# Remove only old individual weapon image resources. Keep the section card image.
for p in list(ASSET_DIR.glob('*')):
    if p.is_file(): p.unlink()
for p in list(DRAWABLE_DIR.glob('*')):
    if p.is_file() and (p.name.startswith('weapon_') or p.stem in {
        'ak47','aug_a3','awm_sniper','barrett_m82','beretta92','desert_eagle','dragunov_svd',
        'energy_cannon','famas','g36','glock19','kriss_vector','laser_gun','m16','m4a1','minigun',
        'mp5','neon_rifle','p90','plasma_rifle','pump_shotgun','revolver','scar_l','shotgun','spas12',
        'tactical_pistol','thompson','usp_tactical','uzi','vector_smg'
    }) and p.name != 'weapon_card.png':
        p.unlink()

for index, (slug, label, category, cell, start, end) in enumerate(weapons, 1):
    x0, y0, x1, y1 = cell_bounds[cell]
    # Keep the weapon art and discard the printed label strip at the bottom.
    crop = im.crop((x0 + 8, y0 + 8, x1 - 8, y1 - 43))
    crop = crop.resize((640, 420), Image.Resampling.LANCZOS)
    crop.save(ASSET_DIR / f'weapon_{index:02d}.webp', 'WEBP', quality=92, method=6)
    crop.save(DRAWABLE_DIR / f'weapon_{index:02d}.webp', 'WEBP', quality=92, method=6)
    subprocess.run([
        'ffmpeg', '-y', '-ss', str(start), '-to', str(end), '-i', str(VIDEO),
        '-vn', '-ac', '1', '-ar', '44100', '-c:a', 'libmp3lame', '-q:a', '2',
        str(RAW_DIR / f'weapon_{slug}.mp3')
    ], check=True, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)

print(f'created {len(weapons)} weapon images and audio clips')
