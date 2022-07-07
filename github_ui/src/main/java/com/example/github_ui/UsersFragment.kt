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
import com.example.github_ui.adapters.UsersAdapter
import com.example.github_ui.databinding.FragmentUsersBinding
import com.example.github_ui.models.GithubUsersModel
import com.example.github_ui.utils.*
import com.example.github_ui.viewmodel.LatestUiState
import com.example.github_ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private val viewModel: MainViewModel by activityViewModels()

    private val binding:FragmentUsersBinding by viewBinding(FragmentUsersBinding::bind)

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
            val action = UsersFragmentDirections.actionUsersFragmentToNavigationDetail(it)
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
                    binding.searchImage.isVisible = false
                    binding.searchGithubMessage.isVisible = false
                }
                is LatestUiState.Success -> {
                    binding.shimmerRecycler.stopShimmer()
                    binding.shimmerRecycler.show(false)
                    binding.rvGithubUsers.show(true)
                    usersAdapter.setUsers(it.users)
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