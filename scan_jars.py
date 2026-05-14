import zipfile, os

jars = [
    r'C:\Users\Administrator\.gradle\caches\forge_gradle\minecraft_repo\versions\1.20.1\client.jar',
    r'C:\Users\Administrator\.gradle\caches\forge_gradle\minecraft_repo\versions\1.20.1\client-extra.jar',
]

out = r'C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement\jar_debug.txt'

with open(out, 'w') as f:
    for jar in jars:
        f.write(f'=== {os.path.basename(jar)} ===\n')
        f.write(f'Exists: {os.path.exists(jar)}, Size: {os.path.getsize(jar)}\n')
        try:
            with zipfile.ZipFile(jar, 'r') as z:
                names = z.namelist()
                f.write(f'Entries: {len(names)}\n')
                pngs = [n for n in names if n.endswith('.png')]
                f.write(f'PNG count: {len(pngs)}\n')
                for p in pngs[:30]:
                    f.write(f'  {p}\n')

                # Also search for netherite
                netherite = [n for n in names if 'netherite' in n.lower()]
                f.write(f'Netherite entries: {len(netherite)}\n')
                for n in netherite[:10]:
                    f.write(f'  {n}\n')

                # armor textures
                armor = [n for n in names if 'armor' in n.lower() and n.endswith('.png')]
                f.write(f'Armor PNGs: {len(armor)}\n')
                for a in armor[:20]:
                    f.write(f'  {a}\n')
        except Exception as e:
            f.write(f'Error: {e}\n')
        f.write('\n')

print('Done')
