package com.tanker.basemodule.adapter;

import android.support.v7.widget.RecyclerView;

import com.tanker.basemodule.event.Selectable;
import com.tanker.basemodule.model.ImageBean;
import com.tanker.basemodule.model.Photo;
import com.tanker.basemodule.model.PhotoDirectory;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectableAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements Selectable {

    private static final String TAG = SelectableAdapter.class.getSimpleName();

    protected List<PhotoDirectory> photoDirectories;
    protected List<ImageBean> selectedPhotos;

    public int currentDirectoryIndex = 0;


    public SelectableAdapter() {
        photoDirectories = new ArrayList<>();
        selectedPhotos = new ArrayList<>();
    }


    /**
     * Indicates if the item at position where is selected
     *
     * @param photo Photo of the item to check
     * @return true if the item is selected, false otherwise
     */
    @Override
    public boolean isSelected(Photo photo) {
        return getSelectedPhotos().contains(new ImageBean(photo.getPath()));
    }

    /**
     * Toggle the selection status of the item at a given position
     *
     * @param photo Photo of the item to toggle the selection status for
     */
    @Override
    public void toggleSelection(Photo photo) {
        ImageBean imageBean = new ImageBean(photo.getPath());
        if (selectedPhotos.contains(imageBean)) {
            selectedPhotos.remove(imageBean);
        } else {
            selectedPhotos.add(imageBean);
        }
    }


    /**
     * Clear the selection status for all items
     */
    @Override
    public void clearSelection() {
        selectedPhotos.clear();
    }


    /**
     * Count the selected items
     *
     * @return Selected items count
     */
    @Override
    public int getSelectedItemCount() {
        return selectedPhotos.size();
    }


    public void setCurrentDirectoryIndex(int currentDirectoryIndex) {
        this.currentDirectoryIndex = currentDirectoryIndex;
    }


    public List<Photo> getCurrentPhotos() {
        return photoDirectories.get(currentDirectoryIndex).getPhotos();
    }


    public ArrayList<ImageBean> getCurrentPhotoPaths() {
        ArrayList<ImageBean> currentPhotoPaths = new ArrayList<>(getCurrentPhotos().size());
        for (Photo photo : getCurrentPhotos()) {
            currentPhotoPaths.add(new ImageBean(photo.getPath()));
        }
        return currentPhotoPaths;
    }


    public List<ImageBean> getSelectedPhotos() {
        return selectedPhotos;
    }

}