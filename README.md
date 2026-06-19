# My Demo App — Android Test Automation

Appium + Java + TestNG mobile automation framework for the [Sauce Labs "My Demo
App"](https://github.com/saucelabs/my-demo-app-android) Android application,
built with the **Page Object Model** and Appium's **PageFactory**
(`@AndroidFindBy` + `AppiumFieldDecorator`).

> This repository covers **Task #1 (Mobile Test Automation)**. Task #2 is
> delivered as a separate repository, per the assignment.

## What is covered

| Area | Test class | Scenarios |
|---|---|---|
| Login / logout | `LoginLogoutTests` | successful login, logout back to the Login screen |
| Navigation | `NavigationTests` | catalog → product detail, drawer → About, header → cart, drawer → back to catalog |
| Add to cart / form submission | `CartAndCheckoutTests` | add to cart updates badge & cart screen, full end-to-end checkout (shipping → payment → review → place order) |
| Validation messages | `ValidationTests` | empty username/password, locked-out user, empty shipping form, guest checkout redirect |

All resource-ids and app behavior used by the page objects were verified
directly against the app's source (`saucelabs/my-demo-app-android`), not
guessed — see the Javadoc comments in `src/test/java/.../pages` for the
specific behavior each locator/flow relies on.

## Architecture

```
src/test/java/com/mydemoapp/automation/
├── config/      ConfigReader        — loads config.properties, overridable via -D system properties
├── driver/       AppiumServerManager — starts/stops a local Appium server for the run
│                DriverFactory       — builds/quits the AndroidDriver (UiAutomator2)
├── pages/        BasePage           — PageFactory init + small element-action wrappers
│   components/   AppHeader          — header bar (menu/cart) composed into pages that show it
│                 LoginPage, CatalogPage, ProductDetailPage, CartPage,
│                 CheckoutInfoPage, CheckoutPaymentPage, PlaceOrderPage,
│                 CheckoutCompletePage, MenuPage, AboutPage
├── models/       ShippingAddress, PaymentCard — immutable test-data carriers (records)
├── base/         BaseTest           — Appium session lifecycle (TestNG before/after hooks)
├── listeners/    AllureScreenshotListener — attaches a screenshot to Allure on test failure
└── tests/        LoginLogoutTests, NavigationTests, CartAndCheckoutTests, ValidationTests
```

Each page object exposes intention-revealing methods (`loginAsStandardUser()`,
`addToCart()`, `proceedToCheckout()`...) that return the page object for
wherever the app navigates to next, so tests read as a sequence of user
actions and compile-time-checked navigation, not raw driver calls.

## Prerequisites

1. **JDK 17+** and **Maven 3.8+**
2. **Node.js 18+** and **Appium 2.x** with the UiAutomator2 driver:
   ```bash
   npm install -g appium
   appium driver install uiautomator2
   ```
3. **Android SDK** with `adb` on your `PATH`, and either:
   - a running emulator (Android Studio → Device Manager → start a device), or
   - a physical device connected with USB debugging enabled.

   Verify a device is visible before running tests:
   ```bash
   adb devices
   ```
4. (Optional) [Allure commandline](https://allurereport.org/docs/install/) if
   you prefer `allure serve` over the Maven plugin — not required, see below.

## Configuration

All settings live in `src/test/resources/config.properties` and can be
overridden per run with a matching `-D` flag, e.g.:

```bash
mvn test -Ddevice.name=Pixel_7_API_34 -Dplatform.version=14
```

Key properties:

| Property | Default | Meaning |
|---|---|---|
| `appium.server.autostart` | `true` | Framework starts/stops its own local Appium server. Set `false` to attach to one you manage yourself (e.g. a cloud grid) and set `appium.server.url` accordingly. |
| `device.name` | `emulator-5554` | Target device/emulator (matches `adb devices` output). |
| `platform.version` | *(blank)* | Left blank to auto-detect; set explicitly for a device matrix. |
| `full.reset` | `true` | Reinstalls the app fresh every test for isolation (no leaked login/cart state between tests). |

The APK itself is **not** committed to this repository — see below.

## Setup & run

```bash
# 1. Clone and enter the repo
git clone <this-repo-url>
cd my-demo-app-android-tests

# 2. Run the suite
mvn test
```

`mvn test` will, in order:
1. Download the app under test (`mda-2.2.0-25.apk` from the official
   [my-demo-app-android releases](https://github.com/saucelabs/my-demo-app-android/releases))
   into `apps/`, if it isn't already there.
2. Start a local Appium server (unless disabled, see Configuration).
3. Run all four TestNG test classes defined in `testng.xml`, reinstalling the
   app fresh for every test method.
4. Write Allure result files to `target/allure-results/`.

To run a single test class or method:

```bash
mvn test -Dtest=LoginLogoutTests
mvn test -Dtest=LoginLogoutTests#loginWithValidCredentialsSucceeds
```

## Test report

A human-readable Allure HTML report is generated from `target/allure-results`
on demand (Allure results are produced on every `mvn test` run regardless):

```bash
# Build the static report into target/site/allure-maven-plugin and open it
mvn allure:report
open target/site/allure-maven-plugin/index.html   # macOS; use xdg-open on Linux

# Or build + serve it in one step (opens a browser automatically)
mvn allure:serve
```

The report includes a step-by-step breakdown of every test (via `@Step`
annotations on the page objects) and an attached device screenshot for any
test that fails.

## Design notes

- **Why PageFactory + `@AndroidFindBy`**: lazy element lookup means a page
  object never holds a stale element reference across navigations or app
  reinstalls — Appium re-resolves the locator on each interaction.
- **Why `full.reset=true`**: every test starts from a clean install with an
  empty cart and no logged-in user, so tests can run in any order and a
  failure in one never cascades into the next.
- **`AppHeader` as a composed component**: the hamburger menu / cart icon
  belong to `MainActivity`'s layout, not any individual fragment, so they are
  modeled once and composed into every page that shows them instead of being
  redeclared per page.
- **`CatalogPage#selectProduct`**: the app wires the "open product detail"
  click listener to the product *image*, not its title text. The locator
  finds the title by visible text and then steps to the sibling image via
  `UiSelector#fromParent`, which is robust to catalog re-ordering — it never
  assumes a fixed list index.

## Troubleshooting

- **`Could not find a connected Android device`**: run `adb devices` and
  confirm exactly one device/emulator is listed and matches `device.name`.
- **`App under test not found at .../apps/...`**: run `mvn generate-test-resources`
  (or any `mvn test`) to trigger the download step, or place the APK there
  manually and point `app.path`/`-Dapp.path` at it.
- **Port 4723 already in use**: another Appium server is already running.
  Either stop it, or set `appium.server.autostart=false` and point
  `appium.server.url` at the one that's already up.
