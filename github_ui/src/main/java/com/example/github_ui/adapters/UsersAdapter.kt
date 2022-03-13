package com.example.github_ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_ui.adapters.viewHolders.EmptyStateViewHolder
import com.example.github_ui.adapters.viewHolders.UserViewHolder
import com.example.github_ui.models.GithubUsersModel
import com.example.github_ui.utils.FavoriteUserCallback
import com.example.github_ui.utils.OpenUserDetailsCallback
import javax.inject.Inject

class UsersAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listOfUsers = mutableListOf<GithubUsersModel>()
    var openUsersCallback: OpenUserDetailsCallback? = null
    var favoriteUsersCallback: FavoriteUserCallback? = null


    fun setUsers(items: List<GithubUsersModel>) {
        listOfUsers.clear()
        listOfUsers.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            ITEM_TYPE.NORMAL_ITEM.value -> UserViewHolder.create(
                parent, openUsersCallback ?: {}, favoriteUsersCallback!!
            )
            else -> EmptyStateViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> holder.bind(listOfUsers[position])
            is EmptyStateViewHolder -> holder.bind(true)
        }
    }

    override fun getItemCount(): Int = listOfUsers.size + if (showEmptyState()) 1 else 0

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

    private fun showEmptyState(): Boolean {
        return listOfUsers.isEmpty()
    }
}