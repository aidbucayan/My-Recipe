package com.adrianbucayan.myrecipeapp.presentation.ui.recipe

import android.graphics.Rect
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrianbucayan.myrecipeapp.databinding.RowRecipeIngredientsBinding
import com.adrianbucayan.myrecipeapp.presentation.util.adapter.BaseRecyclerViewAdapter
import com.adrianbucayan.myrecipeapp.presentation.util.adapter.BaseViewHolder
import com.adrianbucayan.myrecipeapp.presentation.util.adapter.ViewHolderInitializer
import kotlinx.coroutines.ExperimentalCoroutinesApi

class RecipeIngredientsListAdapter : BaseRecyclerViewAdapter<String, RowRecipeIngredientsBinding>() ,
    ViewHolderInitializer<String, RowRecipeIngredientsBinding> {

    init { viewBindingInitializer = this }

    class TopSpacingDecoration(private val padding: Int): RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)

            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.top = padding
            }
        }
    }

    override fun generateViewHolder(parent: ViewGroup): BaseViewHolder<String, RowRecipeIngredientsBinding> {

        val itemBinding: RowRecipeIngredientsBinding = RowRecipeIngredientsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return RecipeIngredientsListAdapterViewHolder(itemBinding)

    }

}

@ExperimentalCoroutinesApi
class RecipeIngredientsListAdapterViewHolder(
    viewBinding: RowRecipeIngredientsBinding) : BaseViewHolder<String, RowRecipeIngredientsBinding>(viewBinding) {

    private val title : TextView = viewBinding.rowRecipeIngredients
    private val holder : RelativeLayout = viewBinding.rowRecipeHolder

    override fun setViews(item: String) {
        super.setViews(item)

        title.text = stripHtml(item)

    }

    fun stripHtml(html: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(html).toString()
        }
    }

}

