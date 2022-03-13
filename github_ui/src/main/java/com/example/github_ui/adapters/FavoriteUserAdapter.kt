package com.example.github_ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_ui.adapters.viewHolders.EmptyStateViewHolder
import com.example.github_ui.adapters.viewHolders.FavoriteUserViewHolder
import com.example.github_ui.models.GithubUsersModel
import com.example.github_ui.utils.FavoriteUserCallback
import javax.inject.Inject

class FavoriteUserAdapter @Inject constructor(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var openDetailsCallback: ((GithubUsersModel) -> Unit)? = null
    var favoriteUserCallback: FavoriteUserCallback? = null
    private var favoriteUsers = mutableListOf<GithubUsersModel>()

    fun setFavoriteUsersList(items: List<GithubUsersModel>) {
        this.favoriteUsers.clear()
        this.favoriteUsers.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE.NORMAL_ITEM.value -> FavoriteUserViewHolder.create(
                parent,
                openDetailsCallback ?: {}, favoriteUserCallback!!
            )
            else -> EmptyStateViewHolder.create(parent)
        }
    }

    override fun getItemCount(): Int = favoriteUsers.size + if (showEmptyState()) 1 else 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteUserViewHolder -> holder.bind(favoriteUsers[position])
            is EmptyStateViewHolder -> holder.bind(false)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (showEmptyState()) {
            (ITEM_TYPE.EMPTY_STATE_ITEM.value)
        } else {
            ITEM_TYPE.NORMAL_ITEM.value
        }
    }

    enum class ITEM_TYPE(val value: Int) {
        NORMAL_ITEM(1), EMPTY_STATE_ITEM(2)
    }

    private fun showEmptyState(): Boolean = favoriteUsers.isEmpty()
}