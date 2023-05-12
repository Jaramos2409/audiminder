package gg.jrg.audiminder.collections.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.databinding.FragmentCreateNewCollectionBinding

@AndroidEntryPoint
class CreateNewCollectionFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewCollectionBinding
    private val createNewCollectionViewModel by viewModels<CreateNewCollectionViewModel>()
    private val navigationViewModel by activityViewModels<NavigationViewModel>()
    private val args: CreateNewCollectionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNewCollectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        args.album?.let { album ->
            createNewCollectionViewModel.setAlbum(album)
        }

        binding.cancelButton.setOnClickListener {
            navigationViewModel.navigate(NavEvent.Back)
        }

        return binding.root
    }

}