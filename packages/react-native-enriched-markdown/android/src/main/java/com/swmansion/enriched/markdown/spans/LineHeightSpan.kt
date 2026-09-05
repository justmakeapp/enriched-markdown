package com.swmansion.enriched.markdown.spans

import android.graphics.Paint
import android.text.Spanned
import kotlin.math.ceil
import kotlin.math.floor
import android.text.style.LineHeightSpan as AndroidLineHeightSpan

class LineHeightSpan(
  height: Float,
) : AndroidLineHeightSpan {
  private val lineHeight: Int = ceil(height.toDouble()).toInt()

  override fun chooseHeight(
    text: CharSequence?,
    start: Int,
    end: Int,
    spanstartv: Int,
    v: Int,
    fm: Paint.FontMetricsInt?,
  ) {
    if (fm == null) return
    if (hasBlockImage(text, start, end)) return

    val leading = lineHeight - ((-fm.ascent) + fm.descent)
    fm.ascent -= ceil(leading / 2.0f).toInt()
    fm.descent += floor(leading / 2.0f).toInt()
  }

  /**
   * A block [ImageSpan] sizes its line to the image box through its own
   * [AndroidLineHeightSpan]. StaticLayout runs every LineHeightSpan of the
   * enclosing '\n'-delimited paragraph on *every* line of that paragraph, so
   * when an image shares a paragraph with text (`![img](url)` followed by a
   * soft break, which renders as a space) this span would run on the image's
   * line too and reset it to the text line height — the image then paints
   * outside the space reserved for it, over the following content.
   *
   * Excluding the image's character range at build time is not enough for that
   * case, hence the per-line check here.
   */
  private fun hasBlockImage(
    text: CharSequence?,
    start: Int,
    end: Int,
  ): Boolean {
    if (text !is Spanned || end <= start) return false
    return text.getSpans(start, end, ImageSpan::class.java).any { !it.isInline }
  }
}
