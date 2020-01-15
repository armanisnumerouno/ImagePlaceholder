package com.example.images.view.albumpreview

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.images.R
import com.example.images.entities.Album
import com.example.images.entities.Event
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import kotlinx.android.synthetic.main.view_image_preview.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumPreviewFragment : Fragment() {

    private val viewModel by viewModel<AlbumPreviewViewModel>()
    private val disposable = CompositeDisposable()
    private val adapter = AlbumPreviewAdapter(
        object : AlbumPreviewAdapter.Listener {
            override fun onItemClicked(imageUrl: String) {
                showImagePreview(imageUrl)
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        disposable.add(viewModel.output.subscribe { subscribeToViewModel(it) })
        val albumId = arguments?.getInt(ALBUM_ID)
        if (albumId != null) viewModel.fetchAlbum(albumId)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun initView() {
        albums_recycler_view?.layoutManager = GridLayoutManager(context, 3)
        albums_recycler_view?.adapter = adapter
    }

    private fun subscribeToViewModel(event: Event) {
        when (event) {
            is Event.Progress -> showProgress(event.inProgress)
            is Event.Error -> showError(event.message)
            is Event.Data<*> -> setList(event.data as List<Album>)
        }
    }

    private fun showProgress(show: Boolean) {
        // show progress
    }

    private fun showError(message: String) {
        Snackbar.make(albums_recycler_view, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setList(list: List<Album>) {
        adapter.addToList(list)
    }

    private fun showImagePreview(imageUrl: String) {
        val dialog = Dialog(context!!)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val view = layoutInflater.inflate(R.layout.view_image_preview,null)

        Picasso.get()
            .load(imageUrl)
            .into(view.image_preview)

        view.close_icon?.setOnClickListener { dialog.dismiss() }

        dialog.setContentView(view)
        dialog.show()
    }

    companion object {

        private const val ALBUM_ID = "ALBUM_ID"

        fun newInstance(albumId: Int) = AlbumPreviewFragment().apply {
            arguments = Bundle().apply {
                putInt(ALBUM_ID, albumId)
            }
        }
    }
}