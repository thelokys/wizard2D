#!/usr/bin/env python3
"""
Script para gerar spritesheets básicos de animação a partir dos sprites atuais.
Cria spritesheets duplicando os sprites existentes para criar animações simples.
"""

from PIL import Image
import os

sprite_dir = 'src/main/resources/entities/player/sprites'

# Carrega sprites base
idle_right_path = os.path.join(sprite_dir, 'idle-right.png')
idle_left_path = os.path.join(sprite_dir, 'idle-left.png')

if not os.path.exists(idle_right_path) or not os.path.exists(idle_left_path):
    print("Erro: Sprites base não encontrados!")
    exit(1)

idle_right = Image.open(idle_right_path)
idle_left = Image.open(idle_left_path)

sprite_size = idle_right.width
frames = 4  # Número de frames por animação

# Cria spritesheets para cada animação
animations = [
    ('walk-right', idle_right),
    ('walk-left', idle_left),
    ('run-right', idle_right),
    ('run-left', idle_left),
    ('dash-right', idle_right),
    ('dash-left', idle_left),
]

for name, base_img in animations:
    # Cria uma imagem horizontal com múltiplos frames
    sheet = Image.new('RGBA', (sprite_size * frames, sprite_size))
    
    # Duplica o sprite base para criar frames
    for i in range(frames):
        sheet.paste(base_img, (i * sprite_size, 0))
    
    output_path = os.path.join(sprite_dir, f'{name}.png')
    sheet.save(output_path)
    print(f'✓ Criado {name}.png ({sprite_size * frames}x{sprite_size})')

print("\n✓ Todos os spritesheets foram criados!")
print("Nota: Estes são spritesheets básicos usando o sprite atual duplicado.")
print("Você pode substituí-los por spritesheets animados mais elaborados depois.")

