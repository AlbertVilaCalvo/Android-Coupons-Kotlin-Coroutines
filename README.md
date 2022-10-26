# Coupons Android app

Coupons is a simple native Android app that I did for a job interview process.

The app is built entirely with Kotlin. It uses [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous work, [Koin](https://github.com/InsertKoinIO/koin) for Dependency Injection and [Coil](https://github.com/coil-kt/coil) to load images.

There are unit tests for one Presenter and one Use Case, using [JUnit](https://junit.org/junit4/) and [Mockito-Kotlin](https://github.com/mockito/mockito-kotlin).

It has 2 screens. The main screen shows a list of coupons, and by clicking a coupon on the list you go to the detail screen for that coupon.

Coupons are retrieved from a fake API and cached in-memory once obtained.

You can activate and deactivate a coupon by clicking a button, which also hits the fake API and updates the coupons cached in-memory.

## Screenshots

### Main screen - `CouponListActivity`

<img src="screenshots/Captura de pantalla 2022-10-26 a les 11.24.16.png" alt="Main screen">

<br>

### Coupon detail screen - `CouponDetailActivity`

<img src="screenshots/Captura de pantalla 2022-10-26 a les 11.24.27.png" alt="Coupon detail screen">
