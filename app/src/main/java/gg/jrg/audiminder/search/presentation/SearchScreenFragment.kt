package gg.jrg.audiminder.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.databinding.FragmentSearchScreenBinding
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.presentation.AuthorizeMusicServiceBottomSheetFragment
import gg.jrg.audiminder.music_services.presentation.MusicServiceViewModel

@AndroidEntryPoint
class SearchScreenFragment : Fragment() {

    private lateinit var binding: FragmentSearchScreenBinding
    private val musicServiceViewModel by activityViewModels<MusicServiceViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchServiceBar.setOnClickListener {
            if (!musicServiceViewModel.isThisServiceAuthorized(MusicServiceType.SPOTIFY)) {
                AuthorizeMusicServiceBottomSheetFragment().show(
                    parentFragmentManager,
                    AuthorizeMusicServiceBottomSheetFragment.TAG
                )
            }
        }

        return binding.root
    }

}