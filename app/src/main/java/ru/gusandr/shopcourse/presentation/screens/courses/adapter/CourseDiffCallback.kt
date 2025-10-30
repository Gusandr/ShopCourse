package ru.gusandr.shopcourse.presentation.screens.courses.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.gusandr.domain.model.Course

class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }
}