package com.pklein.mariage.ui.atome

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.rotateRad
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.pklein.mariage.ui.theme.ColorPrimaryDark
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// Everything is drawn in a 300 x 300 logical space, then scaled to the available width.
private const val SIZE = 300f
private const val LEFT = 40f
private const val TOP = 40f
private const val RIGHT = 260f
private const val BOTTOM = 260f
private const val CHAMFER = 36f
private val Center = Offset(150f, 150f)

private val IceDark = Color(0xFF9ED8F0)
private val ShardColor = Color(0xFFCDEFFA)
private val MarbleVein = Color(0xFF8A8A92)
private val JuiceColor = Color(0xFFF7E04A)
private val JuiceSurface = Color(0xFFFFF3A6)
private val LemonRind = Color(0xFFF2C811)
private val LemonFlesh = Color(0xFFFFE97A)

enum class GlassContent { CrushedIce, LemonJuice }

private class BlockPalette(
    val light: Color,
    val dark: Color,
    val border: Color,
    val shard: Color,
    val shardEdge: Color,
    val crackShadow: Color,
    val crackCore: Color,
    val opacityStart: Float,
    val opacityEnd: Float,
)

private val IcePalette =
    BlockPalette(
        light = Color(0xFFFFFFFF),
        dark = IceDark,
        border = Color(0xFFBFE6F5),
        shard = ShardColor,
        shardEdge = Color.White,
        crackShadow = Color(0x80005E85),
        crackCore = Color.White,
        opacityStart = 0.8f,
        opacityEnd = 0.5f,
    )

private val MarblePalette =
    BlockPalette(
        light = Color(0xFFF7F7F4),
        dark = Color(0xFFB4B4BA),
        border = Color(0xFF8E8E96),
        shard = Color(0xFFDCDCE0),
        shardEdge = Color(0xFF8E8E96),
        crackShadow = Color(0x66FFFFFF),
        crackCore = Color(0xFF55555C),
        opacityStart = 0.97f,
        opacityEnd = 0.85f,
    )

private fun GlassContent.blockPalette() =
    when (this) {
        GlassContent.CrushedIce -> IcePalette
        GlassContent.LemonJuice -> MarblePalette
    }

private val MarbleVeins: List<List<Offset>> =
    listOf(
        listOf(Offset(48f, 118f), Offset(82f, 126f), Offset(104f, 156f), Offset(146f, 166f), Offset(176f, 198f)),
        listOf(Offset(104f, 156f), Offset(98f, 190f), Offset(120f, 224f)),
        listOf(Offset(190f, 52f), Offset(206f, 84f), Offset(238f, 98f), Offset(252f, 130f)),
        listOf(Offset(206f, 84f), Offset(194f, 116f), Offset(214f, 148f)),
    )

private val OctagonVertices =
    listOf(
        Offset(LEFT + CHAMFER, TOP),
        Offset(RIGHT - CHAMFER, TOP),
        Offset(RIGHT, TOP + CHAMFER),
        Offset(RIGHT, BOTTOM - CHAMFER),
        Offset(RIGHT - CHAMFER, BOTTOM),
        Offset(LEFT + CHAMFER, BOTTOM),
        Offset(LEFT, BOTTOM - CHAMFER),
        Offset(LEFT, TOP + CHAMFER),
    )

private val OctagonPath = polygon(OctagonVertices)

// One crack appears per hit, the last one right before the block explodes.
private val Cracks: List<List<Offset>> =
    listOf(
        listOf(Offset(150f, 150f), Offset(128f, 118f), Offset(135f, 85f), Offset(120f, 50f)),
        listOf(Offset(150f, 150f), Offset(182f, 130f), Offset(205f, 100f), Offset(238f, 86f)),
        listOf(Offset(150f, 150f), Offset(170f, 185f), Offset(160f, 220f), Offset(185f, 252f)),
        listOf(Offset(150f, 150f), Offset(118f, 172f), Offset(92f, 170f), Offset(62f, 196f)),
        listOf(Offset(128f, 118f), Offset(100f, 110f), Offset(70f, 88f)),
        listOf(Offset(182f, 130f), Offset(215f, 142f), Offset(240f, 138f), Offset(258f, 160f)),
        listOf(Offset(160f, 220f), Offset(130f, 232f), Offset(118f, 254f)),
        listOf(Offset(135f, 85f), Offset(160f, 70f), Offset(170f, 46f)),
        listOf(Offset(92f, 170f), Offset(75f, 140f), Offset(46f, 128f)),
        listOf(Offset(215f, 142f), Offset(225f, 175f), Offset(240f, 215f), Offset(252f, 232f)),
    )

private val CrushedIceChunks =
    listOf(
        Triple(122f, 106f, 12f),
        Triple(138f, 104f, 40f),
        Triple(154f, 106f, 70f),
        Triple(170f, 104f, 25f),
        Triple(184f, 108f, 55f),
        Triple(130f, 118f, 80f),
        Triple(146f, 116f, 15f),
        Triple(162f, 118f, 60f),
        Triple(176f, 118f, 35f),
        Triple(140f, 129f, 50f),
        Triple(156f, 128f, 20f),
        Triple(150f, 140f, 65f),
    )

private class Shard(
    val path: Path,
    val centroid: Offset,
    val direction: Offset,
    val speed: Float,
    val spin: Float,
)

private val Shards = buildShards()

private fun polygon(points: List<Offset>) =
    Path().apply {
        moveTo(points.first().x, points.first().y)
        points.drop(1).forEach { lineTo(it.x, it.y) }
        close()
    }

private fun buildShards(): List<Shard> {
    val random = Random(7)
    val outer =
        OctagonVertices.indices.flatMap { i ->
            val a = OctagonVertices[i]
            val b = OctagonVertices[(i + 1) % OctagonVertices.size]
            (0 until 3).map { step -> a + (b - a) * (step / 3f) }
        }
    val inner = outer.map { Center + (it - Center) * 0.5f }

    return outer.indices.flatMap { j ->
        val next = (j + 1) % outer.size
        listOf(
            listOf(Center, inner[j], inner[next]),
            listOf(inner[j], outer[j], outer[next], inner[next]),
        ).map { points ->
            val centroid = points.reduce { a, b -> a + b } / points.size.toFloat()
            val away = centroid - Center
            Shard(
                path = polygon(points),
                centroid = centroid,
                direction = away / away.getDistance(),
                speed = 80f + random.nextFloat() * 120f,
                spin = (random.nextFloat() - 0.5f) * 6f,
            )
        }
    }
}

@Composable
fun IceBlock(
    state: IceBlockState,
    description: String,
    glassContent: GlassContent,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    val pulse = remember { Animatable(1f) }
    val shatter = remember { Animatable(if (state.isShattered) 1f else 0f) }
    val cracks = animateFloatAsState(state.hits.toFloat(), tween(150), label = "cracks")

    LaunchedEffect(state) { state.healWhenIdle() }
    LaunchedEffect(state.isShattered) {
        if (state.isShattered && shatter.value < 1f) {
            shatter.animateTo(
                1f,
                tween(1100, easing = LinearEasing),
            )
        }
    }

    Canvas(
        modifier =
            modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .semantics { contentDescription = description }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    enabled = !state.isShattered,
                ) {
                    state.tap()
                    haptic.performHapticFeedback(
                        if (state.isShattered) HapticFeedbackType.LongPress else HapticFeedbackType.TextHandleMove,
                    )
                    scope.launch {
                        pulse.snapTo(0.94f)
                        pulse.animateTo(1f, spring(Spring.DampingRatioMediumBouncy))
                    }
                },
    ) {
        scale(scale = size.width / SIZE, pivot = Offset.Zero) {
            if (state.isShattered) {
                drawGlass(glassContent, fill = shatter.value)
                Shards.forEach { drawShard(it, shatter.value, glassContent.blockPalette()) }
            } else {
                scale(pulse.value, pivot = Center) {
                    drawGlass(glassContent, fill = 0f)
                    drawBlock(cracks.value, glassContent)
                }
            }
        }
    }
}

private fun DrawScope.drawGlass(
    content: GlassContent,
    fill: Float,
) {
    val bowl = polygon(listOf(Offset(105f, 100f), Offset(195f, 100f), Offset(150f, 165f)))
    drawPath(bowl, Color.White.copy(alpha = 0.5f))
    if (fill > 0f) {
        when (content) {
            GlassContent.CrushedIce -> drawCrushedIce(bowl, fill)
            GlassContent.LemonJuice -> drawLemonJuice(fill)
        }
    }
    drawPath(bowl, ColorPrimaryDark, style = Stroke(width = 6f))
    drawLine(ColorPrimaryDark, Offset(150f, 165f), Offset(150f, 205f), 6f, StrokeCap.Round)
    drawLine(ColorPrimaryDark, Offset(122f, 207f), Offset(178f, 207f), 6f, StrokeCap.Round)
    if (fill > 0f && content == GlassContent.LemonJuice) drawLemonSlice(fill)
}

private fun DrawScope.drawCrushedIce(
    bowl: Path,
    fill: Float,
) {
    drawPath(bowl, IceDark.copy(alpha = 0.5f * fill))
    CrushedIceChunks.forEach { (x, y, angle) ->
        rotate(angle, pivot = Offset(x, y)) {
            drawRect(ShardColor, Offset(x - 5f, y - 5f), Size(10f, 10f), alpha = fill)
            drawRect(
                Color.White,
                Offset(x - 5f, y - 5f),
                Size(10f, 10f),
                alpha = fill,
                style = Stroke(1.5f),
            )
        }
    }
}

private fun DrawScope.drawLemonJuice(fill: Float) {
    val surface = 112f
    val inset = (surface - 100f) * (45f / 65f)
    val left = Offset(105f + inset, surface)
    val right = Offset(195f - inset, surface)
    drawPath(polygon(listOf(left, right, Offset(150f, 165f))), JuiceColor, alpha = 0.9f * fill)
    drawLine(JuiceSurface, left, right, 4f, StrokeCap.Round, alpha = fill)
    listOf(
        Offset(138f, 130f) to 3.5f,
        Offset(158f, 124f) to 2.5f,
        Offset(150f, 142f) to 3f,
    ).forEach { (center, radius) ->
        drawCircle(Color.White, radius, center, alpha = 0.7f * fill)
    }
}

private fun DrawScope.drawLemonSlice(fill: Float) {
    val center = Offset(196f, 98f)
    drawCircle(LemonRind, 17f, center, alpha = fill)
    drawCircle(Color.White, 14f, center, alpha = fill)
    drawCircle(LemonFlesh, 11.5f, center, alpha = fill)
    repeat(8) {
        val angle = it * (Math.PI.toFloat() / 4f)
        drawLine(
            Color.White,
            center,
            center + Offset(cos(angle), sin(angle)) * 11.5f,
            1.5f,
            alpha = fill,
        )
    }
}

private fun DrawScope.drawBlock(
    crackLevel: Float,
    content: GlassContent,
) {
    val palette = content.blockPalette()
    val opacity =
        palette.opacityStart - (palette.opacityStart - palette.opacityEnd) * (crackLevel / HITS_TO_SHATTER)
    val brush =
        Brush.linearGradient(
            listOf(palette.light, palette.dark),
            start = Offset(LEFT, TOP),
            end = Offset(RIGHT, BOTTOM),
        )
    drawPath(OctagonPath, brush, alpha = opacity)
    drawPath(OctagonPath, palette.border, style = Stroke(width = 3f))
    if (content == GlassContent.LemonJuice) {
        MarbleVeins.forEach { vein ->
            vein.zipWithNext { a, b -> drawLine(MarbleVein, a, b, 2f, StrokeCap.Round, alpha = 0.45f) }
        }
    }
    drawLine(
        Color.White.copy(alpha = 0.7f),
        Offset(70f, 84f),
        Offset(108f, 62f),
        5f,
        StrokeCap.Round,
    )
    drawLine(
        Color.White.copy(alpha = 0.5f),
        Offset(70f, 100f),
        Offset(82f, 93f),
        5f,
        StrokeCap.Round,
    )
    Cracks.forEachIndexed { index, crack ->
        drawCrack(
            crack,
            (crackLevel - index).coerceIn(0f, 1f),
            palette,
        )
    }
}

private fun DrawScope.drawCrack(
    points: List<Offset>,
    fraction: Float,
    palette: BlockPalette,
) {
    if (fraction <= 0f) return
    val lengths = points.zipWithNext { a, b -> (b - a).getDistance() }
    var remaining = lengths.sum() * fraction
    for (i in lengths.indices) {
        val a = points[i]
        val b = points[i + 1]
        val end = if (remaining >= lengths[i]) b else a + (b - a) * (remaining / lengths[i])
        drawLine(palette.crackShadow, a, end, 5f, StrokeCap.Round)
        drawLine(palette.crackCore, a, end, 2.5f, StrokeCap.Round)
        remaining -= lengths[i]
        if (remaining <= 0f) break
    }
}

private fun DrawScope.drawShard(
    shard: Shard,
    progress: Float,
    palette: BlockPalette,
) {
    val offset = shard.direction * (shard.speed * progress) + Offset(0f, 220f * progress * progress)
    val alpha = (1f - (progress - 0.4f) / 0.6f).coerceIn(0f, 1f)
    translate(offset.x, offset.y) {
        rotateRad(shard.spin * progress, pivot = shard.centroid) {
            drawPath(shard.path, palette.shard, alpha = alpha * 0.9f)
            drawPath(shard.path, palette.shardEdge, alpha = alpha, style = Stroke(2f))
        }
    }
}

@Preview(showBackground = true, name = "Intact")
@Composable
private fun IceBlockPreview() {
    IceBlock(state = IceBlockState(), description = "", glassContent = GlassContent.CrushedIce)
}

@Preview(showBackground = true, name = "Fissuré")
@Composable
private fun IceBlockCrackedPreview() {
    IceBlock(
        state = IceBlockState(initialHits = 7),
        description = "",
        glassContent = GlassContent.CrushedIce,
    )
}

@Preview(showBackground = true, name = "Brisé")
@Composable
private fun IceBlockShatteredPreview() {
    IceBlock(
        state = IceBlockState(initialHits = HITS_TO_SHATTER, initialShattered = true),
        description = "",
        glassContent = GlassContent.CrushedIce,
    )
}

@Preview(showBackground = true, name = "Marbre fissuré")
@Composable
private fun MarbleBlockCrackedPreview() {
    IceBlock(
        state = IceBlockState(initialHits = 7),
        description = "",
        glassContent = GlassContent.LemonJuice,
    )
}

@Preview(showBackground = true, name = "Brisé, jus de citron")
@Composable
private fun IceBlockLemonJuicePreview() {
    IceBlock(
        state = IceBlockState(initialHits = HITS_TO_SHATTER, initialShattered = true),
        description = "",
        glassContent = GlassContent.LemonJuice,
    )
}
