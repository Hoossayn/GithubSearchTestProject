package com.example.github_ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_ui.adapters.viewHolders.EmptyStateViewHolder
import com.example.github_ui.adapters.viewHolders.RepoViewHolder
import com.example.github_ui.models.GithubRepoModel
import javax.inject.Inject

class RepoAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val repoList = mutableListOf<GithubRepoModel>()

    fun setRepo(items: List<GithubRepoModel>){
        repoList.clear()
        repoList.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_TYPE.NORMAL_ITEM.value -> RepoViewHolder.create(
                parent
            )
            else -> EmptyStateViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RepoViewHolder -> holder.bind(repoList[position])
            is EmptyStateViewHolder -> holder.bind(true)
        }
    }

    override fun getItemCount(): Int = repoList.size + if (showEmptyState()) 1 else 0

    enum class ITEM_TYPE(val value: Int) {
        NORMAL_ITEM(1), EMPTY_STATE_ITEM(2)
    }

    private fun showEmptyState(): Boolean {
        return repoList.isEmpty()
    }

}