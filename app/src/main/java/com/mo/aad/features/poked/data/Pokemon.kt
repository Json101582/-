/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mo.aad.features.poked.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Pokemon(
  var page: Int = 0,
  @SerializedName("name") @PrimaryKey val name: String,
  @SerializedName("url") val url: String
) : Parcelable {

  fun getImageUrl(): String {
    val index = url.split("/".toRegex()).dropLast(1).last()
    return "https://pokeres.bastionbot.org/images/pokemon/$index.png"
  }

  override fun toString(): String {
    return "Pokemon(page=$page, name='$name', url='$url')"
  }


}
