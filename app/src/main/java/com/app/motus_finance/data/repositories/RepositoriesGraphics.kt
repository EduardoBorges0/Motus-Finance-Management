package com.app.motus_finance.data.repositories

import com.app.motus_finance.data.dao.GraphicsDAO
import com.app.motus_finance.data.models.Graphics

class RepositoriesGraphics(private val dao: GraphicsDAO) {

    suspend fun insertGraphics(graphics: Graphics){
        return dao.insertGraphics(graphics)
    }
}