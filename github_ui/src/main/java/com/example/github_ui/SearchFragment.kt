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


        binding.users.setOnClickListener {
            val userAction  = SearchFragmentDirections.actionNavigationHomeToUsersFragment()
            findNavController().navigate(userAction)
        }

        binding.repos.setOnClickListener {
            val repoAction = SearchFragmentDirections.actionNavigationHomeToRepositoriesFragment()
            findNavController().navigate(repoAction)
        }

    }
}