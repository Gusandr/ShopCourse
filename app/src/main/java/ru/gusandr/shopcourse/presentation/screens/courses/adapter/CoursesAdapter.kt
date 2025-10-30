package ru.gusandr.shopcourse.presentation.screens.courses.adapter

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.gusandr.domain.model.Course
import ru.gusandr.shopcourse.R
import ru.gusandr.shopcourse.databinding.ItemCourseCardBinding
import java.time.format.DateTimeFormatter

class CoursesAdapter(
    private val onLikeCourseClick: (Int) -> Unit
) : ListAdapter<Course, CoursesAdapter.CourseViewHolder>(CourseDiffCallback()) {

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
        holder.bind(course, onLikeCourseClick)
    }

    class CourseViewHolder(
        private val binding: ItemCourseCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course, onLikeCourseClick: (Int) -> Unit) {
            binding.apply {
                tvTitle.text = course.title
                tvDescription.text = course.text
                tvPrice.text = course.price
                tvRating.text = course.rate.toString()
                tvDate.text = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(course.publishDate)
                imgCourse.setImageResource(R.drawable.ic_courses_icon_person)
                btnFavorite.setOnClickListener {
                    onLikeCourseClick(course.id)
                }
                ivFavorite.isSelected = course.hasLike
                ivFavorite.setImageResource(
                    if (course.hasLike) R.drawable.ic_bookmark_filled
                    else R.drawable.ic_bookmark
                )
                btnMore.setOnClickListener {
                    // экран курса
                }
            }
        }
    }
}