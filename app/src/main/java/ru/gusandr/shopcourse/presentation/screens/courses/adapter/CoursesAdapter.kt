package ru.gusandr.shopcourse.presentation.screens.courses.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.gusandr.domain.model.Course
import ru.gusandr.shopcourse.R
import ru.gusandr.shopcourse.databinding.ItemCourseCardBinding

class CoursesAdapter : ListAdapter<Course, CoursesAdapter.CourseViewHolder>(CourseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position)
        holder.bind(course)
    }

    class CourseViewHolder(
        private val binding: ItemCourseCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.apply {
                tvTitle.text = course.title
                tvDescription.text = course.text
                tvPrice.text = course.price
                tvRating.text = course.displayRate
                tvDate.text = course.displayPublishDate
                imgCourse.setImageResource(getCourseImage())
                btnFavorite.setOnClickListener {
                    // TODO: избранное
                }
                btnMore.setOnClickListener {
                    // TODO: экран курса
                }
            }
        }

        private fun getCourseImage(): Int {
            val courseImages = arrayOf(
                R.drawable.ic_courses_icon_person
            )
            return courseImages.random()
        }
    }
}