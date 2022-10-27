package com.bgaprojects.pokebird.ui.detailsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bgaprojects.pokebird.databinding.FragmentDetailsBinding
import com.bgaprojects.pokebird.ui.base.BaseFragment

class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {
    override val viewModel: DetailsViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
}