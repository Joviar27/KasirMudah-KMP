package com.cobasendiri.kasirmudahkmp.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kasirmudah_kmp.shared.generated.resources.Poppins_Bold
import kasirmudah_kmp.shared.generated.resources.Poppins_Medium
import kasirmudah_kmp.shared.generated.resources.Poppins_SemiBold
import kasirmudah_kmp.shared.generated.resources.Res
import org.jetbrains.compose.resources.Font

val PoppinsFontFamily: FontFamily
    @Composable get() = FontFamily (
        Font(Res.font.Poppins_Bold, weight = FontWeight.Bold),
        Font(Res.font.Poppins_SemiBold, weight = FontWeight.SemiBold),
        Font(Res.font.Poppins_Medium, weight = FontWeight.Medium)
    )

val baseline = androidx.compose.material3.Typography()

@Composable
fun kasirMudahTypography(): Typography{
    val poppinsFontFamily = PoppinsFontFamily
    return Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = poppinsFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = poppinsFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = poppinsFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = poppinsFontFamily),
        headlineMedium = baseline.headlineMedium.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            color = OnPrimary
        ), //28.sp
        headlineSmall = baseline.headlineSmall.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            color = OnPrimary
        ), //24.sp
        titleLarge = baseline.titleLarge.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = OnPrimary
        ),//24.sp
        titleMedium = baseline.titleMedium.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = OnPrimary
        ),//16.sp
        titleSmall = baseline.titleSmall.copy(fontFamily = poppinsFontFamily),
        bodyLarge = baseline.bodyLarge.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = OnPrimary
        ),//16.sp
        bodyMedium = baseline.bodyMedium.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = OnPrimary
        ),//14.sp
        bodySmall = baseline.bodySmall.copy(fontFamily = poppinsFontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = poppinsFontFamily),
        labelMedium = baseline.labelMedium.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            color = OnPrimary
        ),//12.sp
        labelSmall = baseline.labelSmall.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Light,
            color = OnPrimary
        ),//11.sp
    )
}
