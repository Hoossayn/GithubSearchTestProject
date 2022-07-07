package com.example.github_ui.adapters.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_ui.databinding.ItemReposBinding
import com.example.github_ui.databinding.ItemUsersBinding
import com.example.github_ui.models.GithubRepoModel

class RepoViewHolder (
    private val binding: ItemReposBinding
    ) :RecyclerView.ViewHolder(binding.root){

        fun bind(item:GithubRepoModel){
            item.run {
                binding.repoName.text = fullName
                binding.reposDesc.text = description
            }
        }

    companion object {
        fun create(
            parent: ViewGroup
        ): RepoViewHolder {
            val binding = ItemReposBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RepoViewHolder(binding)
        }
    }
}