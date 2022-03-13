package com.example.github_ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github_ui.adapters.UsersAdapter
import com.example.github_ui.databinding.FragmentSearchBinding
import com.example.github_ui.models.GithubUsersModel
import com.example.github_ui.utils.*
import com.example.github_ui.viewmodel.LatestUiState
import com.example.github_ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel: MainViewModel by activityViewModels()

    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)

    @Inject
    lateinit var usersAdapter: UsersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            binding.searchGithubEditText.textChange()
                .debounce(1000)
                .collect {
                    if (it?.length!! >= 2) {
                        viewModel.setQueryInfo(it.toString())
                    }
                }
        }


        lifecycleScope.launchWhenStarted {
            binding.rvGithubUsers.observeRecycler()
                .collect {
                    if (it) {
                        viewModel.fetchMoreUsers()
                    }
                }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.searchGithubUsers()
        }
        usersAdapter.openUsersCallback = {
            val action = SearchFragmentDirections.actionNavigationHomeToNavigationFavorite(it)
            findNavController().navigate(action)
        }

        usersAdapter.favoriteUsersCallback = { user ->
            viewModel.favoriteUser(user)
        }

        observe(viewModel.users, ::subscribeToUi)
        observe(viewModel.isLoadingMore, ::observeLoading)
    }

    private fun setupRecyclerView() {
        binding.rvGithubUsers.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.VERTICAL, false
            )
            addItemDecoration(MarginItemDecoration(16))
            adapter = usersAdapter
        }
    }

    private fun subscribeToUi(users: LatestUiState<List<GithubUsersModel>>?) {
        users?.let {
            when (it) {
                is LatestUiState.Loading -> {
                    binding.shimmerRecycler.show(true)
                    binding.shimmerRecycler.startShimmer()
                    binding.rvGithubUsers.show(false)
                    binding.swipeRefresh.isRefreshing = false
                }
                is LatestUiState.Success -> {
                    binding.shimmerRecycler.stopShimmer()
                    binding.shimmerRecycler.show(false)
                    binding.rvGithubUsers.show(true)
                    usersAdapter.setUsers(it.users)
                    binding.swipeRefresh.isRefreshing = false
                }
                is LatestUiState.Error -> {
                    requireContext().showToast(it.exception, Toast.LENGTH_LONG)
                    binding.loadMore.show(false)
                    binding.shimmerRecycler.show(false)
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun observeLoading(isLoading: Boolean?) {
        isLoading?.let {
            if (it) {
                binding.loadMore.show(true)
            } else {
                binding.loadMore.show(false)
            }
        }
    }
}