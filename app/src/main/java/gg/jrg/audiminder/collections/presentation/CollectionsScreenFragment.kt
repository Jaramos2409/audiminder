package gg.jrg.audiminder.collections.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.R
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.databinding.FragmentCollectionsScreenBinding

@AndroidEntryPoint
class CollectionsScreenFragment : Fragment() {

    private lateinit var binding: FragmentCollectionsScreenBinding
    private val collectionsViewModel by viewModels<CollectionsViewModel>()
    private val navigationViewModel by activityViewModels<NavigationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionsScreenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.collectionsTopBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.collections_add_new -> {
                    navigationViewModel.navigate(
                        NavEvent.To(
                            CollectionsScreenFragmentDirections.actionCollectionsScreenFragmentToCreateNewCollectionFragment(
                                null
                            )
                        )
                    )
                    true
                }

                else -> false
            }
        }

        return binding.root
    }

}