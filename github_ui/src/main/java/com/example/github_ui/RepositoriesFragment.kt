package com.example.github_ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github_ui.adapters.RepoAdapter
import com.example.github_ui.databinding.FragmentRepositoriesBinding
import com.example.github_ui.models.GithubRepoModel
import com.example.github_ui.models.GithubUsersModel
import com.example.github_ui.utils.*
import com.example.github_ui.viewmodel.LatestUiState
import com.example.github_ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@AndroidEntryPoint
class RepositoriesFragment : Fragment(R.layout.fragment_repositories) {

    private val viewModel: MainViewModel by activityViewModels()

    private val binding: FragmentRepositoriesBinding by viewBinding(FragmentRepositoriesBinding::bind)


    @Inject
    lateinit var repoAdapter: RepoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            binding.searchRepoEditText.textChange()
                .debounce(1000)
                .collect {
                    if (it?.length!! >= 2) {
                        viewModel.setQueryInfoForRepo(it.toString())
                    }
                }
        }


        lifecycleScope.launchWhenStarted {
            binding.rvGithubRepos.observeRecycler()
                .collect {
                    if (it) {
                        viewModel.fetchMoreUsers()
                    }
                }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.searchRepo()
        }


        observe(viewModel.repos, ::subscribeToUi)
       // observe(viewModel.isLoadingMore, ::observeLoading)
    }



    private fun setupRecyclerView(){
        binding.rvGithubRepos.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.VERTICAL, false
            )
            addItemDecoration(MarginItemDecoration(16))
            adapter = repoAdapter
        }
    }


    private fun subscribeToUi(repos: LatestUiState<List<GithubRepoModel>>?) {
        repos?.let {
            when (it) {
                is LatestUiState.Loading -> {
                    binding.shimmerRecycler.show(true)
                    binding.shimmerRecycler.startShimmer()
                    binding.rvGithubRepos.show(false)
                    binding.swipeRefresh.isRefreshing = false
                    binding.searchImage.isVisible = false
                    binding.searchGithubMessage.isVisible = false
                }
                is LatestUiState.Success -> {
                    binding.shimmerRecycler.stopShimmer()
                    binding.shimmerRecycler.show(false)
                    binding.rvGithubRepos.show(true)
                    repoAdapter.setRepo(it.users)
                    binding.swipeRefresh.isRefreshing = false
                    binding.searchImage.isVisible = false
                    binding.searchGithubMessage.isVisible = false
                }
                is LatestUiState.Error -> {
                    requireContext().showToast(it.exception, Toast.LENGTH_LONG)
                    binding.loadMore.show(false)
                    binding.shimmerRecycler.show(false)
                    binding.swipeRefresh.isRefreshing = false
                    binding.searchImage.isVisible = true
                    binding.searchGithubMessage.isVisible = true
                    binding.searchGithubMessage.text = resources.getString(R.string.github_users_not_found)
                }
            }
        }
    }



}