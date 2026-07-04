# 🚀 NMS Guide — App Android Offline

<div align="center">
  <img src="assets/banner.png" alt="NMS Guide Banner" width="600"/>
  <br><br>
  <strong>Tu guía completa de No Man's Sky, siempre contigo, sin internet.</strong>
  <br><br>
  <img src="https://img.shields.io/badge/Kotlin-2.0.0-7F52FF?logo=kotlin" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Compose-BOM%202024.06-4285F4?logo=jetpackcompose" alt="Compose"/>
  <img src="https://img.shields.io/badge/API-26%2B-brightgreen" alt="Min SDK"/>
  <img src="https://img.shields.io/badge/license-MIT-blue" alt="License"/>
</div>

---

## ✨ Características

- 📖 **Guías completas** — Glifos, nanites, mercurio, unidades, naves, supervivencia, bases
- 🔍 **Búsqueda instantánea** — Encuentra cualquier guía en segundos
- ⭐ **Favoritos** — Guarda tus artículos preferidos
- 🌙 **Tema oscuro NMS** — Diseño espacial con acentos neón
- 📴 **100% offline** — Todos los datos incluidos en la app
- 🆓 **Sin anuncios** — Open source, sin trackers

## 📱 Capturas

| Guías | Búsqueda | Detalle | Favoritos |
|-------|----------|---------|-----------|
| ![Home](screenshots/home.png) | ![Search](screenshots/search.png) | ![Detail](screenshots/detail.png) | ![Favorites](screenshots/favorites.png) |

## 🛠️ Tecnologías

- **Kotlin** 100% — Zero Java
- **Jetpack Compose** + Material 3
- **Kotlinx Serialization** — Parseo de JSON
- **Navigation Compose** — Navegación declarativa
- **DataStore** — Preferencias y favoritos
- **MVVM** — Arquitectura limpia

## 📂 Estructura del Proyecto

```
nms-guide-app/
├── app/
│   ├── src/main/
│   │   ├── assets/data/          ← 📄 Datos JSON de las guías
│   │   │   ├── categories.json   ← Índice de categorías
│   │   │   ├── glyphs.json
│   │   │   ├── nanites.json
│   │   │   ├── quicksilver.json
│   │   │   ├── units.json
│   │   │   ├── ships.json
│   │   │   ├── survival.json
│   │   │   ├── bases.json
│   │   │   └── tips.json
│   │   ├── java/com/nmsguide/app/
│   │   │   ├── MainActivity.kt
│   │   │   ├── data/             ← Modelos, Repository, Favorites
│   │   │   ├── ui/
│   │   │   │   ├── theme/        ← Colores NMS, tipografía, tema
│   │   │   │   ├── navigation/   ← NavGraph, BottomNav
│   │   │   │   ├── screens/      ← 6 pantallas (Home, Category, Detail, Search, Favorites, Settings)
│   │   │   │   └── components/   ← Cards, badges, tip boxes
│   │   │   └── viewmodel/        ← GuideViewModel
│   │   └── res/
│   └── build.gradle.kts
├── .github/workflows/
│   ├── build.yml                 ← CI: Build APK en cada push
│   ├── release.yml               ← CD: Release APK con tags
│   └── data-validation.yml       ← Validate JSON en cambios
├── scripts/
│   └── generate_data.sh          ← Script para regenerar datos
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 🚀 Cómo construir

### Requisitos
- Android Studio Ladybug (2024.2+) o IntelliJ IDEA
- JDK 17+
- Gradle 8.6+

### Pasos

```bash
# Clonar
git clone https://github.com/tuusuario/nms-guide-app.git
cd nms-guide-app

# Build debug APK
./gradlew assembleDebug

# APK en:
# app/build/outputs/apk/debug/app-debug.apk
```

## 🤖 GitHub Actions

| Workflow | Evento | Producto |
|----------|--------|----------|
| **Build** | Push a main/develop | APK Debug ⬆️ Artifact |
| **Release** | Tag v*.*.* | APK Release 🏷️ GitHub Release |
| **Data Validation** | Cambios en JSON | Validación de estructura |

### Para Release

```bash
git tag v1.0.0
git push origin v1.0.0
```

Esto genera automáticamente el APK release firmado y lo publica en GitHub Releases.

## 📊 Roadmap

### v1.0 ✅
- [x] Guías completas de NMS (8 categorías)
- [x] Búsqueda full-text
- [x] Favoritos persistentes
- [x] Tema oscuro NMS
- [x] CI/CD con GitHub Actions

### v1.1 🔜
- [ ] Tabla de recetas de refinería
- [ ] Calculadora de rutas comerciales
- [ ] Widget de recordatorio (misiones diarias Nexo)

### v2.0 🔮
- [ ] Imágenes de naves y multitools
- [ ] Catálogo de naves por coordenadas
- [ ] Sincronización multi-dispositivo
- [ ] Modo claro (opcional)

## 🤝 Contribuir

1. Fork el repo
2. Crea tu branch (`git checkout -b feature/mi-mejora`)
3. Edita los JSON en `app/src/main/assets/data/`
4. Commit (`git commit -m 'feat: agrega X'`)
5. Push (`git push origin feature/mi-mejora`)
6. Abre un Pull Request

## 📝 Licencia

MIT — haz lo que quieras con esto, Viajero 🚀

---

<div align="center">
  <p>Hecho con 💙 por la comunidad de No Man's Sky</p>
  <p>No está afiliado a Hello Games. No Man's Sky © Hello Games.</p>
</div>
