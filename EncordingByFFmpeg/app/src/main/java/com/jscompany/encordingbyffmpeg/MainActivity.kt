package com.jscompany.encordingbyffmpeg

import android.content.res.AssetManager
import android.media.MediaMetadataRetriever
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.ExecuteCallback
import com.arthenica.mobileffmpeg.FFmpeg
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val btn : Button by lazy { findViewById(R.id.btn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            encodeVideo()
        }

    }

    private fun encodeVideo() {
        val beforeTime = System.currentTimeMillis()
        val inputAssetFileName = "KakaoTalk_20230922_162043231.mp4" //30초짜리 영상


        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        val inputFilePath = getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath +"/before_"+timeStamp+".mp4";
        val outputFilePath = "${getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath}/${timeStamp}_myStory_1.mp4";

        Log.d("incording inputFilePath = ", inputFilePath)
        Log.d("incording outputFilePath = ", outputFilePath)


        if(copyAssetVideoToInternalStorage(inputAssetFileName, inputFilePath)) {

            val duration = getVideoDuration(inputFilePath)
            var seconds = 0L

            if (duration != null) {
                seconds = duration / 1000 // 밀리초를 초로 변환
                println("비디오 길이: ${seconds}초")
            } else {
                println("비디오 길이를 추출할 수 없습니다.")
            }

//            val cmd = arrayOf(
//                "-i", inputFilePath, // 입력 동영상 경로
//                "-c:v","libx264", // 비디오 코덱 지정 (libx264 사용)
//                "-profile:v", "baseline",
//                "-level:v" ,"1.3",
//                "-b:v", "1000k",
//                "-c:a", "aac", // 오디오 코덱 지정 (AAC 사용)
//                "-b:a", "128k", // 오디오 비트레이트 설정
//                "-ar" ,"44100",
//                "-ac", "2",
//                "-vsync" , "vfr",
//                "-r", "30",
//                outputFilePath) // 출력 동영상 경로

            val cmd = arrayOf(
                "-i", inputFilePath, // 입력 동영상 경로
                "-c:v", "libx264", // 비디오 코덱 지정 (libx264 사용)
                "-profile:v", "baseline",
                "-level:v", "1.3",
                "-b:v", "1000k",
                "-c:a", "copy", // 오디오 코덱 지정 (AAC 사용)
                "-b:a", "128k", // 오디오 비트레이트 설정
                "-ar", "44100",
                "-ac", "2",
                "-t" ,seconds.toString(),
                "-vsync" , "vfr",
                outputFilePath // 출력 동영상 경로
            )

            // mobile-ffmpeg 라이브러리를 사용하여 FFmpeg 명령 실행
            val rc = FFmpeg.executeAsync(cmd, ExecuteCallback { executionId, returnCode ->
                if (returnCode == Config.RETURN_CODE_SUCCESS) {
                    // 동영상 인코딩 성공
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "동영상 인코딩 성공", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "성공")

                        val afterTime = System.currentTimeMillis() // 코드 실행 후에 시간 받아오기
                        val secDiffTime: Long = (afterTime - beforeTime) / 1000 //두 시간에 차 계산
                        Log.d("TAG", "시간차이(s) : $secDiffTime")
                    }
                } else {
                    // 동영상 인코딩 실패
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "동영상 인코딩 실패", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "returnCode = $returnCode")
                        Log.d("TAG", "실패")
                    }
                }
            })
        }

    }

    fun getVideoDuration(filePath: String): Long? {
        try {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(filePath)

            val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val duration = durationStr?.toLong()

            retriever.release()

            return duration
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun copyAssetVideoToInternalStorage(
        assetFileName: String,
        outputPath: String
    ): Boolean {
        return try {
            val assetManager: AssetManager = assets
            val inputStream = assetManager.open(assetFileName)
            val outputStream = FileOutputStream(outputPath)
            Log.d("incording assetFileName = ", assetFileName)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            inputStream.close()
            outputStream.close()
            true // 복사 성공
        } catch (e: IOException) {
            Log.e("incording", "Asset 파일을 앱 내부 저장소로 복사 중 오류 발생: " + e.message)
            false // 복사 실패
        }
    }
}