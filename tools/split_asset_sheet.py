from pathlib import Path
from PIL import Image

SOURCE = Path('/home/ubuntu/upload/1000059408.png')
OUT = Path('/home/ubuntu/weapon-simulator-work/app/src/main/res/drawable-nodpi')
OUT.mkdir(parents=True, exist_ok=True)

# Each tuple is (y0, y1, [(x0, x1), ...]). These are review crops, not yet
# catalog identity claims. The source contact sheet has no labels.
rows = {
    'weapon': [
        (0, 130, [(20,160),(170,300),(320,470),(470,620),(620,780),(770,930),(920,1080),(1070,1225),(1215,1380),(1370,1535)]),
        (130, 255, [(0,190),(185,335),(330,490),(480,650),(640,810),(800,970),(960,1120),(1110,1280),(1270,1410),(1400,1535)]),
        (255, 370, [(0,210),(200,430),(420,650),(640,870),(850,1135),(1115,1535)]),
    ],
    'energy': [
        (365, 545, [(0,170),(160,325),(315,475),(465,625),(585,805),(755,935),(905,1075),(1045,1215),(1185,1375),(1340,1490),(1450,1535)]),
        (535, 700, [(0,175),(155,330),(310,480),(455,660),(635,825),(800,1005),(980,1175),(1145,1340),(1300,1535)]),
    ],
    'barber': [
        (690, 855, [(0,155),(130,300),(275,445),(420,595),(565,745),(720,900),(880,1045),(1025,1190),(1170,1330),(1305,1440),(1415,1535)]),
        (850, 1024, [(0,155),(135,305),(285,455),(435,600),(575,750),(720,900),(875,1050),(1020,1215),(1190,1385),(1360,1535)]),
    ],
}

im = Image.open(SOURCE).convert('RGBA')
for section, section_rows in rows.items():
    n = 0
    for y0, y1, boxes in section_rows:
        for x0, x1 in boxes:
            n += 1
            im.crop((x0, y0, x1, y1)).save(OUT / f'{section}_{n:02d}.png', optimize=True)
    print(section, n)
