package com.adrianbucayan.myrecipeapp.presentation.ui.recipe_list

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrianbucayan.myrecipeapp.R
import com.adrianbucayan.myrecipeapp.databinding.RowRecipeBinding
import com.adrianbucayan.myrecipeapp.domain.model.Recipe
import com.adrianbucayan.myrecipeapp.presentation.util.Utils
import com.bumptech.glide.Glide
import com.adrianbucayan.myrecipeapp.presentation.util.adapter.BaseRecyclerViewAdapter
import com.adrianbucayan.myrecipeapp.presentation.util.adapter.BaseViewHolder
import com.adrianbucayan.myrecipeapp.presentation.util.adapter.ViewHolderInitializer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.DecimalFormat

class RecipeListAdapter : BaseRecyclerViewAdapter<Recipe, RowRecipeBinding>() ,
    ViewHolderInitializer<Recipe, RowRecipeBinding> {

    var toSelectRecipe: ((Recipe, Int) -> Unit)? = null

    init { viewBindingInitializer = this }

    class TopSpacingDecoration(private val padding: Int): RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)

            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.top = padding
            }
        }
    }

    override fun generateViewHolder(parent: ViewGroup): BaseViewHolder<Recipe, RowRecipeBinding> {

        val itemBinding: RowRecipeBinding = RowRecipeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return SubDiscoveryItemsAdapterViewHolder(itemBinding, toSelectRecipe)

    }

    override fun submitList(listItems: List<Recipe>) {
        val itemsToAdd = arrayListOf<Recipe>()
        val newListItems = listItems as ArrayList<Recipe>
        newListItems.forEach { newItem ->
            val existingPost = items.find {
                it.pk == newItem.pk
            }
            if (existingPost == null) {
                itemsToAdd.add(newItem)
            }
        }
        items.addAll(newListItems)
        //itemsFull = ArrayList(items)
        notifyDataSetChanged()
    }

}

@ExperimentalCoroutinesApi
class SubDiscoveryItemsAdapterViewHolder(
    viewBinding: RowRecipeBinding,
    private val toSelectRecipe: ((Recipe, Int) -> Unit)?
) : BaseViewHolder<Recipe, RowRecipeBinding>(viewBinding) {

    private val title : TextView = viewBinding.rowRecipeTitle
    private val rating : TextView = viewBinding.rowRecipeRating
    private val image : ImageView = viewBinding.rowRecipeImage
    private val holder : RelativeLayout = viewBinding.rowRecipeHolder

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun setViews(recipe: Recipe) {
        super.setViews(recipe)

        title.text = recipe.title?.let { stripHtml(it) }
        rating.text = recipe.rating.toString()

        if(!recipe.featuredImage.isNullOrEmpty()) {
            Glide.with(image.context)
                .load(recipe.featuredImage)
                .fitCenter()
                .error(android.R.drawable.ic_menu_report_image)
                .into(image)
        }

        holder.setOnClickListener{
            toSelectRecipe?.invoke(recipe, adapterPosition)
        }

    }

    fun stripHtml(html: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(html).toString()
        }
    }

}

