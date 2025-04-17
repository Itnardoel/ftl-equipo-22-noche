# 🧬 Git Flow - Guía de trabajo colaborativo

Este repositorio sigue la metodología **Git Flow** para mantener un flujo de trabajo limpio, ordenado y escalable entre desarrolladores. Acá vas a encontrar las convenciones que usamos para trabajar en equipo de manera prolija y eficiente.

---

## Ramas principales

- `main`: contiene el código **estable** y listo para producción. Nunca se pushea directamente a esta rama.
- `develop`: contiene el código en estado de desarrollo **estable**. Todas las nuevas features se mergean primero acá.

---

## Ramas secundarias

Usamos ramas temporales para organizar el trabajo:

### Features (`feature/nombre-de-la-feature`)
Para el desarrollo de nuevas funcionalidades.

```bash
git checkout develop
git checkout -b feature/login-de-usuario
```

> [!NOTE]  
> Cuando se termina, se hace PR (pull request) a `develop`.

---

### Hotfixes (`hotfix/nombre-del-fix`)
Para resolver errores críticos directamente en `main`.

```bash
git checkout main
git checkout -b hotfix/fix-login-en-produccion
```

> Una vez solucionado:
> 1. Merge a `main` (para producción).
> 2. Merge a `develop` (para mantener la paridad).

---

### Releases (`release/nombre-de-la-release`)
Para preparar una nueva versión que será publicada en producción.

```bash
git checkout develop
git checkout -b release/v1.0.0
```

> Acá se pueden aplicar últimos fixes y ajustes. Al finalizar:
> - Merge a `main` (con tag de versión).
> - Merge a `develop`.

---

## Convenciones de commits

Usamos [**Conventional Commits**](https://www.conventionalcommits.org/) para que los mensajes de commit sean claros y automatizables:

```
<tipo>[alcance opcional]: descripción breve
```

### Tipos comunes:
- `feat`: nueva funcionalidad
- `fix`: corrección de errores
- `docs`: cambios en la documentación
- `style`: cambios de formato (espacios, comas, etc.)
- `refactor`: cambios internos sin afectar comportamiento
- `test`: agregando/modificando tests
- `chore`: tareas que no afectan la lógica ni el output

### Ejemplos:
```
feat(auth): agregar validación de email
fix(login): corregir error al enviar formulario
docs(readme): agregar guía de instalación
```

---

## Buenas prácticas

- Siempre crear ramas a partir de `develop` (salvo hotfixes).
- Commits chicos y enfocados. Evitá mezclar varios temas en uno.
- Nombrar las ramas en kebab-case (`feature/nombre-largo`).
- Antes de pushear, hacé pull para evitar conflictos.
- Usá PRs para todo. Nada de merge directo a `develop` o `main`.
- Revisar bien el código antes de aprobar un PR.
- Siempre escribir mensajes de commit claros y descriptivos.

---

## Tests y revisión

> [!IMPORTANT]
> No es solo código que funcione, es código que **otros puedan entender y mantener**.

Antes de hacer un PR:
- Revisá que no haya errores ni warnings.
- Asegurate de que tu rama compile y funcione correctamente.
- Pedí revisión a otro miembro del equipo.

---

## Deployment

Los deploys a producción se hacen **solo desde `main`**, luego de mergear una `release/` o `hotfix/`.

---

¡Gracias por mantener el flujo ordenado! 🙌
