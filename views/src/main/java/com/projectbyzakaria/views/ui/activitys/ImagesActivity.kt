package com.projectbyzakaria.views.ui.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.projectbyzakaria.views.databinding.ActivityImagesBinding
import com.projectbyzakaria.views.ui.adapters.ImagesAdapter
import com.projectbyzakaria.views.ui.bottoms.BottomSheat
import com.projectbyzakaria.views.utlis.Constent

class ImagesActivity : AppCompatActivity() {
    lateinit var  binding:ActivityImagesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        val list = intent.getStringArrayListExtra(Constent.KEY_LIST_IMAGES)
        val position = intent.getIntExtra(Constent.POSITION,-1)
        if (position != -1 && list?.isNotEmpty() == true){
            list.add(0,list[position])
            list.removeAt(position+1)
        }

        val adapter = ImagesAdapter{drawable,url->
            val bottomSheetDialog = BottomSheat(this,drawable,url)
            bottomSheetDialog.show()
        }
        list?.toSet()?.toList()?.let { adapter.setList(it) }
        if (!list.isNullOrEmpty()){
            binding.myImages.adapter = adapter
        }else{
            binding.root.removeView(binding.myImages)
        }

    }
}
