# Como criar o Pull Request

## Status do Commit
✅ Commit criado com sucesso na branch `feature/player-animations`

## Próximos passos:

### 1. Fazer push da branch para seu fork

Se você ainda não tem um fork, crie um em: https://github.com/thelokys/wizard2D/fork

Depois, configure o remote do seu fork e faça push:

```bash
# Adicione seu fork como remote (substitua SEU_USUARIO pelo seu usuário do GitHub)
git remote add fork git@github.com:SEU_USUARIO/wizard2D.git

# Ou se preferir usar HTTPS:
# git remote add fork https://github.com/SEU_USUARIO/wizard2D.git

# Faça push da branch
git push -u fork feature/player-animations
```

### 2. Criar o Pull Request

Após o push, você pode criar o PR de duas formas:

#### Opção A: Via GitHub Web Interface
1. Vá para: https://github.com/thelokys/wizard2D
2. GitHub mostrará uma notificação para criar um PR da sua branch
3. Clique em "Compare & pull request"
4. Preencha o título e descrição:

**Título:**
```
feat: Adiciona sistema de animações para o player
```

**Descrição:**
```markdown
## 🎨 Sistema de Animações para o Player

Este PR implementa um sistema completo de animações para o player com diferentes estados de movimento.

### ✨ Funcionalidades

- **Sistema de Animação**: Implementa animações usando `Animator` e `SpriteSheet`
- **Estados de Animação**: 
  - `idle`: Animação parado com efeito de respiração
  - `walk`: Animação de caminhada com movimento de pernas
  - `run`: Animação de corrida mais dinâmica
  - `dash`: Animação de dash com efeito de velocidade

### 🎮 Controles

- **WASD**: Movimento normal (walk)
- **Shift + WASD**: Corrida (run)
- **Shift durante movimento**: Dash rápido (cooldown de 60 frames)

### 🖼️ Animações Visuais

Cada animação possui 4 frames únicos com transformações reais:
- **Idle**: Movimento vertical sutil (respiração)
- **Walk**: Rotação do corpo e movimento vertical (passos)
- **Run**: Compressão horizontal e rotação acentuada
- **Dash**: Estiramento horizontal, compressão vertical e transparência variável

### 📁 Arquivos Modificados

- `Player.java`: Implementa sistema de animação e estados
- `DirectionInput.java`: Adiciona suporte para Shift
- `AnimationGenerator.java`: Utilitário para gerar animações com transformações
- `SpriteSheetGenerator.java`: Utilitário para gerar spritesheets básicos
- Spritesheets animados (8 arquivos PNG)

### 🛠️ Utilitários

- `AnimationGenerator`: Gera animações com transformações visuais reais
- `SpriteSheetGenerator`: Gera spritesheets básicos (backup)

### 📝 Notas

Os spritesheets foram gerados programaticamente com transformações visuais para criar animações reais, não apenas cópias do mesmo sprite.
```

#### Opção B: Via linha de comando (gh CLI)

Se você tem o GitHub CLI instalado:

```bash
gh pr create --base main --head SEU_USUARIO:feature/player-animations --title "feat: Adiciona sistema de animações para o player" --body "Descrição do PR (veja acima)"
```

### 3. Verificar o commit

O commit inclui:
- ✅ Sistema de animação completo
- ✅ Detecção de estados (idle, walk, run, dash)
- ✅ Sistema de dash com cooldown
- ✅ Animações visuais com transformações reais
- ✅ Spritesheets gerados (8 arquivos PNG)
- ✅ Utilitários de geração de animações

---

**Branch atual:** `feature/player-animations`  
**Commit:** `f73fe81`

