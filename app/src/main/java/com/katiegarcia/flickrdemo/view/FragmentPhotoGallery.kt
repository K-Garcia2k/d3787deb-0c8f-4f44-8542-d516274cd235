package com.katiegarcia.flickrdemo.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.katiegarcia.flickrdemo.R
import com.katiegarcia.flickrdemo.databinding.FragmentPhotoGalleryBinding
import com.katiegarcia.flickrdemo.model.data.FlickrPhoto
import com.katiegarcia.flickrdemo.model.data.events.UserSearch
import com.katiegarcia.flickrdemo.view.adapter.GalleryAdapter
import com.katiegarcia.flickrdemo.view.adapter.GalleryLoadStateAdapter
import com.katiegarcia.flickrdemo.view.model.PhotoGalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/*
Fragment for the core functionality of the PhotoGallery.
uses PhotoGalleryViewModel for handling PagingSource, LiveData and API calls.
 */
@AndroidEntryPoint
class FragmentPhotoGallery : Fragment(R.layout.fragment_photo_gallery), GalleryAdapter.OnItemClickListener {

    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding get() = _binding!!
    val photoViewModel: PhotoGalleryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_gallery_search, menu)

                val searchItem = menu.findItem(R.id.action_gallery_search)
                val searchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if(query != null){
                            binding.rvPhotoGallery.scrollToPosition(0)
                            //Handle the replace here for spaces to allow for multiple tags
                            photoViewModel.setQuery(query, true) //Sets the query to a set of comma separated tags
                            searchView.clearFocus()
                        }
                        return true
                    }
                    //Live TextChanges could be handled for character by character API searches
                    //However this would be expensive. So I've decided for subission only
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
               return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        _binding = FragmentPhotoGalleryBinding.bind(view)
        val adapter = GalleryAdapter(this)
        binding.apply{
            rvPhotoGallery.setHasFixedSize(true)
            rvPhotoGallery.adapter = adapter.withLoadStateHeaderAndFooter(
                header = GalleryLoadStateAdapter { adapter.retry()},
                footer = GalleryLoadStateAdapter { adapter.retry()},
            )
        }

        photoViewModel.photos.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(photo: FlickrPhoto) {
        val action = FragmentPhotoGalleryDirections.actionPhotoGalleryToFragmentPhotoDetails(photo)
        findNavController().navigate(action)
    }

    //Registering GreenRobot's EventBus for handling onMessageEvent on Start
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    //Unregistering GreenRobot's EventBus for handling onMessageEvent
    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    //Function for handling the onClick events that will be sent from the PhotoItems
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(userSearch: UserSearch){
        photoViewModel.setUserID(userSearch.userID)

    }
}