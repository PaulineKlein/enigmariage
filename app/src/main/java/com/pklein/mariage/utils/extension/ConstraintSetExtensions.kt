package com.pklein.mariage.utils.extension

import androidx.constraintlayout.widget.ConstraintSet

/**
 * https://gist.github.com/jossiwolf/b018bd39b5e88bbb55ef02552a5cb67e
 * Merges two [ConstraintSet]s. If a Constraint exists in the first [ConstraintSet] and the second one, the original value will be replaced by the new value.
 */
operator fun ConstraintSet.plus(other: ConstraintSet): ConstraintSet {
    return this.copy().apply { updateWith(other) }
}

/**
 * Clears the existing constraints and sets them to those of the [constraintSet]
 */
fun ConstraintSet.setConstraints(constraintSet: ConstraintSet) {
    val field = ConstraintSet::class.java.getDeclaredField("mConstraints")
    field.isAccessible = true

    val theseConstraints: HashMap<Int, Any> = field.get(this) as HashMap<Int, Any>
    val newConstraints: HashMap<Int, Any> = field.get(constraintSet) as HashMap<Int, Any>

    theseConstraints.clear()
    theseConstraints.putAll(newConstraints)
}

/**
 * Copies the [ConstraintSet] into a new [ConstraintSet]
 */
fun ConstraintSet.copy(): ConstraintSet = ConstraintSet().apply { clone(this@copy) }

/**
 * Clears the constraints of this [ConstraintSet] using reflection
 */
@Suppress("UNCHECKED_CAST")
fun ConstraintSet.clearConstraints() {
    val field = ConstraintSet::class.java.getDeclaredField("mConstraints")
    field.isAccessible = true

    val theseConstraints: HashMap<Int, Any> = field.get(this) as HashMap<Int, Any>
    theseConstraints.clear()
}

/**
 * Mutates the [ConstraintSet] with the values from the [other] [ConstraintSet]
 * @see https://github.com/tristanvda/ConstraintSet-UpdateWith
 */
@Suppress("UNCHECKED_CAST")
fun ConstraintSet.updateWith(other: ConstraintSet) {

    val field = ConstraintSet::class.java.getDeclaredField("mConstraints")
    field.isAccessible = true

    val theseConstraints: HashMap<Int, Any> = field.get(this) as HashMap<Int, Any>
    val newConstraints: HashMap<Int, Any> = field.get(other) as HashMap<Int, Any>

    theseConstraints.putAll(newConstraints)
}