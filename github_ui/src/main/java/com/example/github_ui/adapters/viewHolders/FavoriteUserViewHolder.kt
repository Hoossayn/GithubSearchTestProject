package com.example.github_ui.adapters.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.github_ui.R
import com.example.github_ui.databinding.ItemUsersBinding
import com.example.github_ui.models.GithubUsersModel
import com.example.github_ui.utils.FavoriteUserCallback
import com.example.github_ui.utils.OpenUserDetailsCallback
import com.example.github_ui.utils.loadImage

class FavoriteUserViewHolder(
    private val binding: ItemUsersBinding,
    private val openUsersDetailsCallback: OpenUserDetailsCallback,
    private val favoriteUserCallback: FavoriteUserCallback,
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: GithubUsersModel) {

        item.run {
            binding.imageUser.loadImage(avatarUrl)
            binding.userName.text = login

            binding.userLayout.setOnClickListener {
                openUsersDetailsCallback.invoke(item)
            }
            binding.favoriteUser.setImageDrawable(ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_red_favorite, null))
            binding.favoriteUser.setOnClickListener {
                favoriteUserCallback.invoke(item)
            }
        }

    }

    companion object {
        fun create(
            parent: ViewGroup,
            openUsersDetailsCallback: OpenUserDetailsCallback,
            favoriteUserCallback: FavoriteUserCallback
        ): FavoriteUserViewHolder {
            val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FavoriteUserViewHolder(binding, openUsersDetailsCallback, favoriteUserCallback)
        }
    }
}