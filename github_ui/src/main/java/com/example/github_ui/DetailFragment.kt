package com.example.github_ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.github_ui.databinding.FragmentDetailBinding
import com.example.github_ui.models.GithubUsersModel
import com.example.github_ui.utils.loadImage
import com.example.github_ui.utils.observe
import com.example.github_ui.utils.viewBinding
import com.example.github_ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val userArgs: DetailFragmentArgs by navArgs()

    private val viewModel: MainViewModel by activityViewModels()

    private val binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.toolbar.menu?.get(0)?.setOnMenuItemClickListener {
            viewModel.favoriteUserDetail(userArgs.user)
            return@setOnMenuItemClickListener true
        }

        viewModel.setUserDetail(userArgs.user)

        observe(viewModel.user, ::init)
        observe(viewModel.isFavorite, ::setFavoriteStatus)

    }

    private fun setFavoriteStatus(status: Boolean?) {
        status?.let {
            setFavoriteIcon(it)
        }
    }

    private fun init(user: GithubUsersModel?) {
        user?.let {
            binding.imgBackdrop.loadImage(it.avatarUrl)
            binding.userDetailName.text = it.login
            binding.userGithubLink.text = it.githubUrl
            setFavoriteIcon(it.isFavorite)
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.toolbar.menu?.get(0)?.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_red_favorite)
        } else {
            binding.toolbar.menu?.get(0)?.icon =
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_favorite_border_24
                )
        }
    }

}