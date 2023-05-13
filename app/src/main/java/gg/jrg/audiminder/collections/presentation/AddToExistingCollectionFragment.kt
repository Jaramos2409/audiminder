package gg.jrg.audiminder.collections.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.databinding.FragmentAddToExistingCollectionBinding

@AndroidEntryPoint
class AddToExistingCollectionFragment : Fragment() {

    private lateinit var binding: FragmentAddToExistingCollectionBinding
    private val navigationViewModel by activityViewModels<NavigationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddToExistingCollectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addToExistingCollectionTopBar.setNavigationOnClickListener {
            navigationViewModel.navigate(NavEvent.Back)
        }

        return binding.root
    }

}