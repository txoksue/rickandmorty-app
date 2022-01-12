package com.paradigma.rickandmorty.ui.characters

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paradigma.rickandmorty.R
import com.paradigma.rickandmorty.databinding.FragmentCharactersBinding
import com.paradigma.rickandmorty.domain.Character
import com.paradigma.rickandmorty.ui.ScreenState
import com.paradigma.rickandmorty.ui.characters.adapter.CharactersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var charactersAdapter : CharactersAdapter
    private lateinit var linearLayoutManager : LinearLayoutManager

    private lateinit var characterList : ArrayList<Character?>

    companion object {
        const val ITEMS_LEFT_TO_REQUEST_MORE_ITEMS_INFINITY_SCROLL = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentCharactersBinding.inflate(inflater, container, false )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersAdapter = CharactersAdapter(
            arrayListOf(),
            onClickCharacter = { character -> onClickCharacter(character) })

        characterList = arrayListOf()

        setupRecyclerViewForCharacters()
        setupSwipeLayoutRefresh()

        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_go_to_favorites -> {
                findNavController().navigate(CharactersFragmentDirections.actionCharactersFragmentToFavoritesFragment())
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupSwipeLayoutRefresh() {
        binding.swipeRefreshLayoutCharacterList.setOnRefreshListener {
            viewModel.requestNewCharacterList()
            binding.swipeRefreshLayoutCharacterList.isRefreshing = false
            binding.swipeRefreshLayoutCharacterList.visibility = VISIBLE
        }
    }

    private fun setupRecyclerViewForCharacters() {
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewCharacterList.apply {
            layoutManager = linearLayoutManager
            adapter = charactersAdapter
            setHasFixedSize(true)
        }

        setRecyclerViewScrollListenerForInfinityScroll()
    }

    private fun initObservers() {

        viewModel.statusScreen.observe(viewLifecycleOwner, Observer { statusScreen ->
            when (statusScreen) {
                is ScreenState.Loading -> {
                    with(binding) {
                        layoutLoader.visibility = VISIBLE
                    }
                }
                is ScreenState.Results -> {
                    characterList = ArrayList(statusScreen.data)
                    charactersAdapter.setItems(characterList)
                    with(binding) {
                        recyclerViewCharacterList.visibility = VISIBLE
                        componentCharactersNoResult.visibility = GONE
                        layoutLoader.visibility = GONE
                    }
                }
                is ScreenState.NoData -> {
                    with(binding) {
                        recyclerViewCharacterList.visibility = GONE
                        componentCharactersNoResult.visibility = VISIBLE
                        componentCharactersNoResult.setImage(R.mipmap.no_results_background)
                        componentCharactersNoResult.setError(getString(R.string.characters_no_results))
                        layoutLoader.visibility = GONE
                    }
                }
                is ScreenState.Error -> {
                    with(binding) {
                        recyclerViewCharacterList.visibility = GONE
                        componentCharactersNoResult.visibility = VISIBLE
                        componentCharactersNoResult.setImage(R.mipmap.no_results_background)
                        componentCharactersNoResult.setError(getString(R.string.characters_error))

                        layoutLoader.visibility = GONE
                    }
                }
            }
        })
    }

    private fun setRecyclerViewScrollListenerForInfinityScroll() {
        binding.recyclerViewCharacterList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                val totalItemCount = charactersAdapter.itemCount
                //scroll bottom request
                if (!viewModel.requestingMoreData && (lastVisibleItem == totalItemCount - ITEMS_LEFT_TO_REQUEST_MORE_ITEMS_INFINITY_SCROLL) && !viewModel.isLastPage()) {
                    // null means loader view
                    characterList.add(null)
                    charactersAdapter.setItems(characterList)
                    binding.recyclerViewCharacterList.adapter?.notifyItemInserted(charactersAdapter.itemCount)
                    viewModel.requestMoreCharacters()
                }

                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }



    private fun onClickCharacter(character: Character) {
        val direction = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(character)
        findNavController().navigate(direction)
    }

}