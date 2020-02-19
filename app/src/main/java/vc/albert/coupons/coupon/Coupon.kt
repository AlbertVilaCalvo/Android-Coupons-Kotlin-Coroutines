package vc.albert.coupons.coupon

import java.util.*

/**
 * Created by Albert Vila Calvo on 2020-02-07.
 */
data class Coupon(
    val id: String,
    val name: String,
    val description: String = "Sin gluten",
    val isActivated: Boolean = false,
    val imageUrl: String = "https://placeimg.com/640/280/tech/any",
    val expire: String = "3 días para finalizar",
    val code: String = "80084",
    val percentDiscount: Int = 22
)

val fakeCoupons = listOf(
    Coupon(
        id = UUID.randomUUID().toString(),
        name = "Platano de Canarias"
    ),
    Coupon(
        id = UUID.randomUUID().toString(),
        name = "Patatas sabor jamón",
        imageUrl = "https://placeimg.com/640/280/grayscale"
    ),
    Coupon(
        id = UUID.randomUUID().toString(),
        name = "Ensalada gourmet",
        description = "Fuente de vitamina A"
    ),
    Coupon(
        id = UUID.randomUUID().toString(),
        name = "Bifidus bebible"
    ),
    Coupon(
        id = UUID.randomUUID().toString(),
        name = "Pastillas para lavavajillas"
    )
)
