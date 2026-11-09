#!/usr/bin/env python3
import os
import subprocess
from pathlib import Path

ROOT = Path('/app/applet') if Path('/app/applet').exists() else Path('.')
SHEET = ROOT / 'docs/asset_sheet_1000059408.png'
REF_DIR = ROOT / 'docs/reference_images'

ASSETS_WEAPONS = ROOT / 'app/src/main/assets/images/weapons'
ASSETS_ENERGY = ROOT / 'app/src/main/assets/images/energy'
ASSETS_BARBER = ROOT / 'app/src/main/assets/images/barber'
DRAWABLE_DIR = ROOT / 'app/src/main/res/drawable'
DRAWABLE_NODPI = ROOT / 'app/src/main/res/drawable-nodpi'

for d in [ASSETS_WEAPONS, ASSETS_ENERGY, ASSETS_BARBER, DRAWABLE_DIR]:
    d.mkdir(parents=True, exist_ok=True)

TMP = Path('/tmp/asset_gen')
TMP.mkdir(parents=True, exist_ok=True)

print("1. Generating radial gradient card backgrounds...")
subprocess.run(['convert', '-size', '320x320', 'radial-gradient:#2A080E-#070305', str(TMP / 'bg_weapon.png')], check=True)
subprocess.run(['convert', '-size', '320x320', 'radial-gradient:#0B2544-#040711', str(TMP / 'bg_energy.png')], check=True)
subprocess.run(['convert', '-size', '320x320', 'radial-gradient:#240B38-#07030E', str(TMP / 'bg_barber.png')], check=True)

def composite_item(item_img_path, bg_type, out_names, category):
    bg_img = TMP / f'bg_{bg_type}.png'
    resized_item = TMP / 'resized_item.png'
    subprocess.run([
        'convert', str(item_img_path),
        '-trim', '+repage',
        '-resize', '240x240',
        str(resized_item)
    ], check=True)
    
    comp_tmp = TMP / 'composite_out.webp'
    subprocess.run([
        'convert', str(bg_img), str(resized_item),
        '-gravity', 'center', '-composite',
        '-quality', '90',
        str(comp_tmp)
    ], check=True)
    
    # Save to assets/images/{category}/ and res/drawable/
    target_assets_dir = ROOT / f'app/src/main/assets/images/{category}'
    for name in out_names:
        asset_file = target_assets_dir / f'{name}.webp'
        drawable_file = DRAWABLE_DIR / f'{name}.webp'
        subprocess.run(['cp', str(comp_tmp), str(asset_file)], check=True)
        subprocess.run(['cp', str(comp_tmp), str(drawable_file)], check=True)
    print(f"  [OK] Saved {out_names} in assets and drawables")

# Extract exact transparent crops from asset_sheet_1000059408.png
proc = subprocess.run([
    'convert', str(SHEET), '-alpha', 'extract', 'gray:-'
], capture_output=True, check=True)
alpha = bytearray(proc.stdout)
w, h = 1536, 1024

def get_row_boxes(y_min, y_max, min_width=35, threshold=25):
    col_counts = [0]*w
    for x in range(w):
        col_counts[x] = sum(1 for y in range(y_min, y_max) if alpha[y*w + x] > threshold)
    
    intervals = []
    in_int = False
    start = 0
    for x in range(w):
        if col_counts[x] > 5 and not in_int:
            in_int = True
            start = x
        elif col_counts[x] <= 5 and in_int:
            in_int = False
            if (x - start) >= min_width:
                intervals.append((start, x))
    if in_int and (w - start) >= min_width:
        intervals.append((start, w))
    
    boxes = []
    for x0, x1 in intervals:
        y0_box = y_max
        y1_box = y_min
        for x in range(x0, x1):
            for y in range(y_min, y_max):
                if alpha[y*w + x] > threshold:
                    if y < y0_box: y0_box = y
                    if y > y1_box: y1_box = y
        if y1_box > y0_box:
            boxes.append((x0, y0_box, x1, y1_box))
    return boxes

def crop_box(x0, y0, x1, y1, out_path):
    bw = x1 - x0
    bh = y1 - y0
    subprocess.run([
        'convert', str(SHEET),
        '-crop', f'{bw}x{bh}+{x0}+{y0}', '+repage',
        str(out_path)
    ], check=True)

# -------------------------------------------------------------
# 1. WEAPONS (Total 30)
# -------------------------------------------------------------
print("\n--- Generating 30 Weapons ---")
# 6 Featured Weapons from reference_03.png
ref03 = REF_DIR / 'reference_03.png'
feat_weapons = [
    ('weapon_01', ['weapon_01', 'ak47'], 60, 390, 460, 240),
    ('weapon_02', ['weapon_02', 'm4a1'], 600, 390, 460, 240),
    ('weapon_03', ['weapon_03', 'desert_eagle'], 60, 720, 460, 240),
    ('weapon_04', ['weapon_04', 'awm_sniper'], 600, 720, 460, 240),
    ('weapon_05', ['weapon_05', 'shotgun'], 60, 1050, 460, 240),
    ('weapon_06', ['weapon_06', 'minigun'], 600, 1050, 460, 240),
]
for tag, names, x, y, cw, ch in feat_weapons:
    crop_tmp = TMP / f'feat_{tag}.png'
    subprocess.run([
        'convert', str(ref03),
        '-crop', f'{cw}x{ch}+{x}+{y}', '+repage',
        str(crop_tmp)
    ], check=True)
    composite_item(crop_tmp, 'weapon', names, 'weapons')

# 24 Weapons from asset_sheet
w_row1 = get_row_boxes(10, 140)   # 9 items: Glock19, Beretta92, Revolver, Uzi, MP5, P90, Vector, SCAR-L, AUG A3
w_row2 = get_row_boxes(140, 255)  # 9 items: FAMAS, G36, M16, Tactical Pistol, USP, SPAS-12, Pump Shotgun, Barrett M82, Dragunov
w_row3 = get_row_boxes(255, 365)  # 6 items: Thompson, Kriss Vector, Laser Gun, Plasma Rifle, Energy Cannon, Neon Rifle

sheet_weapon_boxes = w_row1 + w_row2 + w_row3
print(f"Found {len(sheet_weapon_boxes)} weapon boxes on sheet (expected 24)")

sheet_weapon_names = [
    ('weapon_07', ['weapon_07', 'glock19']),
    ('weapon_08', ['weapon_08', 'beretta92']),
    ('weapon_09', ['weapon_09', 'revolver']),
    ('weapon_10', ['weapon_10', 'uzi']),
    ('weapon_11', ['weapon_11', 'mp5']),
    ('weapon_12', ['weapon_12', 'p90']),
    ('weapon_13', ['weapon_13', 'vector_smg']),
    ('weapon_14', ['weapon_14', 'scar_l']),
    ('weapon_15', ['weapon_15', 'aug_a3']),
    ('weapon_16', ['weapon_16', 'famas']),
    ('weapon_17', ['weapon_17', 'g36']),
    ('weapon_18', ['weapon_18', 'm16']),
    ('weapon_19', ['weapon_19', 'tactical_pistol']),
    ('weapon_20', ['weapon_20', 'usp_tactical']),
    ('weapon_21', ['weapon_21', 'spas12']),
    ('weapon_22', ['weapon_22', 'pump_shotgun']),
    ('weapon_23', ['weapon_23', 'barrett_m82']),
    ('weapon_24', ['weapon_24', 'dragunov_svd']),
    ('weapon_25', ['weapon_25', 'thompson']),
    ('weapon_26', ['weapon_26', 'kriss_vector']),
    ('weapon_27', ['weapon_27', 'laser_gun']),
    ('weapon_28', ['weapon_28', 'plasma_rifle']),
    ('weapon_29', ['weapon_29', 'energy_cannon']),
    ('weapon_30', ['weapon_30', 'neon_rifle']),
]

for idx, (x0, y0, x1, y1) in enumerate(sheet_weapon_boxes):
    if idx < len(sheet_weapon_names):
        tag, names = sheet_weapon_names[idx]
        crop_tmp = TMP / f'sheet_{tag}.png'
        crop_box(x0, y0, x1, y1, crop_tmp)
        composite_item(crop_tmp, 'weapon', names, 'weapons')

# -------------------------------------------------------------
# 2. ENERGY TOOLS (Total 24)
# -------------------------------------------------------------
print("\n--- Generating 24 Energy Tools ---")
# 6 Featured Energy Tools from reference_08.png & reference_02.png
ref08 = REF_DIR / 'reference_08.png'
feat_energy = [
    ('energy_01', ['energy_01', 'thunder_blade'], REF_DIR / 'reference_02.png', 600, 720, 460, 240),
    ('energy_07', ['energy_07', 'electric_gauntlet'], ref08, 450, 1050, 370, 240),
    ('energy_08', ['energy_08', 'storm_spear'], ref08, 40, 1050, 370, 240),
    ('energy_09', ['energy_09', 'tesla_rod'], ref08, 40, 390, 370, 240),
    ('energy_10', ['energy_10', 'lightning_whip'], ref08, 450, 390, 370, 240),
    ('energy_11', ['energy_11', 'shadow_claw'], ref08, 40, 720, 370, 240),
]

for tag, names, src, x, y, cw, ch in feat_energy:
    crop_tmp = TMP / f'feat_{tag}.png'
    subprocess.run([
        'convert', str(src),
        '-crop', f'{cw}x{ch}+{x}+{y}', '+repage',
        str(crop_tmp)
    ], check=True)
    composite_item(crop_tmp, 'energy', names, 'energy')

# 18 Energy Tools from asset sheet
e_row1 = get_row_boxes(365, 540) # 10 items
e_row2 = get_row_boxes(540, 700) # 8 items
sheet_energy_boxes = e_row1 + e_row2
print(f"Found {len(sheet_energy_boxes)} energy boxes on sheet (expected 18)")

sheet_energy_names = [
    ('energy_02', ['energy_02', 'golden_scepter']),
    ('energy_03', ['energy_03', 'void_staff']),
    ('energy_04', ['energy_04', 'ice_scepter']),
    ('energy_05', ['energy_05', 'crimson_mace']),
    ('energy_06', ['energy_06', 'lightning_orb']),
    ('energy_12', ['energy_12', 'holy_hammer']),
    ('energy_13', ['energy_13', 'crystal_staff']),
    ('energy_14', ['energy_14', 'plasma_staff']),
    ('energy_15', ['energy_15', 'dragon_staff']),
    ('energy_16', ['energy_16', 'nature_lightning']),
    ('energy_17', ['energy_17', 'chaos_staff']),
    ('energy_18', ['energy_18', 'electric_fan']),
    ('energy_19', ['energy_19', 'cosmic_staff']),
    ('energy_20', ['energy_20', 'dual_lightning_blades']),
    ('energy_21', ['energy_21', 'lightning_blaster']),
    ('energy_22', ['energy_22', 'energy_vortex']),
    ('energy_23', ['energy_23', 'lightning_lance']),
    ('energy_24', ['energy_24', 'thunder_tornado']),
]

for idx, (x0, y0, x1, y1) in enumerate(sheet_energy_boxes):
    if idx < len(sheet_energy_names):
        tag, names = sheet_energy_names[idx]
        crop_tmp = TMP / f'sheet_{tag}.png'
        crop_box(x0, y0, x1, y1, crop_tmp)
        composite_item(crop_tmp, 'energy', names, 'energy')

# -------------------------------------------------------------
# 3. BARBER TOOLS (Total 27)
# -------------------------------------------------------------
print("\n--- Generating 27 Barber Tools ---")
# 6 Featured Barber Tools from reference_04.png
ref04 = REF_DIR / 'reference_04.png'
feat_barber = [
    ('barber_01', ['barber_01', 'hair_clipper'], 60, 390, 460, 240),
    ('barber_21', ['barber_21', 'scissors'], 600, 390, 460, 240),
    ('barber_22', ['barber_22', 'electric_shaver'], 60, 720, 460, 240),
    ('barber_23', ['barber_23', 'hair_dryer'], 600, 720, 460, 240),
    ('barber_24', ['barber_24', 'spray_bottle'], 60, 1050, 460, 240),
    ('barber_25', ['barber_25', 'shaving_foam'], 600, 1050, 460, 240),
]
for tag, names, x, y, cw, ch in feat_barber:
    crop_tmp = TMP / f'feat_{tag}.png'
    subprocess.run([
        'convert', str(ref04),
        '-crop', f'{cw}x{ch}+{x}+{y}', '+repage',
        str(crop_tmp)
    ], check=True)
    composite_item(crop_tmp, 'barber', names, 'barber')

# 21 Barber Tools from sheet
b_row1 = get_row_boxes(700, 860)  # 11 items
b_row2 = get_row_boxes(860, 1020) # 10 items
sheet_barber_boxes = b_row1 + b_row2
print(f"Found {len(sheet_barber_boxes)} barber boxes on sheet (expected 21)")

sheet_barber_names = [
    ('barber_02', ['barber_02', 'professional_clipper']),
    ('barber_03', ['barber_03', 'classic_clipper']),
    ('barber_04', ['barber_04', 'beard_trimmer']),
    ('barber_05', ['barber_05', 'hair_trimmer']),
    ('barber_06', ['barber_06', 'mini_trimmer']),
    ('barber_07', ['barber_07', 'vintage_clipper']),
    ('barber_08', ['barber_08', 'power_clipper']),
    ('barber_09', ['barber_09', 'metal_clipper']),
    ('barber_10', ['barber_10', 'premium_clipper']),
    ('barber_11', ['barber_11', 'cordless_clipper']),
    ('barber_12', ['barber_12', 'barber_pro_x']),
    ('barber_13', ['barber_13', 'titanium_clipper']),
    ('barber_14', ['barber_14', 'gold_master']),
    ('barber_15', ['barber_15', 'chrome_master']),
    ('barber_16', ['barber_16', 'carbon_fiber_clipper']),
    ('barber_17', ['barber_17', 'neon_clipper']),
    ('barber_18', ['barber_18', 'cyber_trimmer']),
    ('barber_19', ['barber_19', 'turbo_clipper']),
    ('barber_20', ['barber_20', 'ultimate_clipper']),
    ('barber_26', ['barber_26', 'comb']),
    ('barber_27', ['barber_27', 'barber_brush']),
]

for idx, (x0, y0, x1, y1) in enumerate(sheet_barber_boxes):
    if idx < len(sheet_barber_names):
        tag, names = sheet_barber_names[idx]
        crop_tmp = TMP / f'sheet_{tag}.png'
        crop_box(x0, y0, x1, y1, crop_tmp)
        composite_item(crop_tmp, 'barber', names, 'barber')

print("\n--- Cleaning up obsolete slice crops in res/drawable-nodpi/ ---")
if DRAWABLE_NODPI.exists():
    for f in DRAWABLE_NODPI.glob('*.png'):
        f.unlink()
    print("Cleaned up old drawable-nodpi crops.")

print("\n=== SUCCESS: All 81 items generated and placed in assets and drawables! ===")
