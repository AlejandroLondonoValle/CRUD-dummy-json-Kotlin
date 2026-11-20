# CRUD DummyJSON – Kotlin

Aplicación Android desarrollada en **Kotlin** con **Jetpack Compose** que consume la API pública [DummyJSON](https://dummyjson.com/docs/products) para listar, consultar, crear, editar y eliminar productos. Está construida con **Clean Architecture**, **MVVM** e inyección de dependencias con **Hilt**.

## Funcionalidades

- Listado de productos con imagen, marca, categoría, calificación, stock, precio y descuento.
- Detalle de producto en una pantalla dedicada.
- Crear un producto nuevo desde un formulario.
- Editar un producto existente.
- Eliminar un producto con diálogo de confirmación.
- Estados de carga, error (con reintento) y lista vacía.
- Mensajes de éxito y error con Snackbar.
- Tema claro y oscuro con una paleta tipo marketplace.

> **Nota:** DummyJSON simula las operaciones de escritura (POST, PUT, PATCH y DELETE). La API responde como si el cambio se hubiera hecho, pero no guarda nada en el servidor. La app refleja cada cambio en la lista local.

## Tecnologías

| Categoría | Librería |
|---|---|
| Lenguaje | Kotlin 2.2.10 |
| UI | Jetpack Compose (BOM 2026.02.01) + Material 3 |
| Navegación | Navigation Compose 2.8.5 con rutas type-safe (kotlinx.serialization) |
| Red | Retrofit 3.0.0 + Moshi 1.15.2 (codegen con KSP) |
| Inyección de dependencias | Hilt 2.60.1 |
| Imágenes | Coil 3.3.0 |
| Asincronía | Kotlin Coroutines + StateFlow |
| Build | Gradle (Kotlin DSL) + Version Catalog, AGP 9.4.1 |

## Arquitectura

```
com.sena.dummy_json
├── data
│   ├── mapper          # ProductX → ProductModel
│   ├── remote
│   │   ├── api         # ProductService (Retrofit)
│   │   └── dto         # Modelos de respuesta y de petición
│   └── repository      # ProductRepositoryImpl
├── di                  # NetworkModule, RepositoryModule (Hilt)
├── domain
│   ├── model           # ProductModel
│   ├── repository      # Contrato ProductRepository
│   └── useCase         # GetProductUseCase
├── navegation          # Routes y AppNavigation (NavHost)
├── ui
│   ├── composable      # ProductCard, diálogos
│   ├── screen          # Lista, detalle y formulario
│   ├── section         # Secciones de lista, carga, error y vacío
│   ├── state           # ProductUIState
│   ├── theme           # Colores, tema y tipografía
│   └── viewModel       # ProductViewModel
├── Application.kt      # @HiltAndroidApp
└── MainActivity.kt     # @AndroidEntryPoint
```

**Flujo de datos:** `UI (Compose)` → `ProductViewModel` → `ProductRepository` (dominio) → `ProductRepositoryImpl` (datos) → `ProductService` (Retrofit) → DummyJSON.

## Endpoints usados

URL base: `https://dummyjson.com/`

| Método | Endpoint | Uso |
|---|---|---|
| GET | `products` | Listar productos |
| GET | `products/{id}` | Obtener un producto |
| POST | `products/add` | Crear un producto |
| PUT | `products/{id}` | Actualizar un producto |
| PATCH | `products/{id}` | Actualizar campos sueltos |
| DELETE | `products/{id}` | Eliminar un producto |

## Requisitos

- Android Studio reciente, compatible con AGP 9.4.1.
- JDK 17 o superior (el proyecto compila con target Java 11).
- `minSdk` 30 (Android 11) y `targetSdk`/`compileSdk` 37.
- Conexión a internet.

## Instalación

```bash
git clone https://github.com/AlejandroLondonoValle/CRUD-dummy-json-Kotlin.git
```

1. Abre el proyecto en Android Studio.
2. Espera a que termine la sincronización de Gradle.
3. Ejecuta la configuración `app` en un emulador o dispositivo.

## Flujo de trabajo Git

- `main`: versiones estables. Contiene la estructura inicial y los releases (`v1.0.0`).
- `develop`: rama de integración.
- `feature/*`: una rama por capa o funcionalidad, unida a `develop` con `--no-ff`:
  `setup-dependencies`, `domain-layer`, `data-layer`, `dependency-injection`, `ui-theme`, `ui-state`, `ui-components`, `ui-screens`, `navigation`.

Los mensajes de commit siguen [Conventional Commits](https://www.conventionalcommits.org/es/) (`feat`, `build`, `style`, `chore`, etc.).

## Autor

**Alejandro Londoño Valle** – [@AlejandroLondonoValle](https://github.com/AlejandroLondonoValle)
