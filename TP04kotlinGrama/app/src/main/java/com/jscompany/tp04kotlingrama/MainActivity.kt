package com.jscompany.tp04kotlingrama

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    //[2] 지연초기화 - 메모리 낭비를 방지하기 위해 나옴
    // 주로 클래스를 지연초기화 하고 싶을 떄 사용한다~!
    var aaa : String = "test" // 자원 낭비

    //1. lateinit var - 코드 상에서 추후 값을 넣음 단, 기본형에선 사용 불가
    lateinit var bbb:String
    lateinit var person: Person // 원래 목적 클래스를 미리 메모리에 올리지않고 사용하기 위해 나옴

    //2. val 변수명 by lazy {변수에 들어갈 클래스생성자}
    // 기본형에서도 사용가능
    val age by lazy { Person() } //값을 바꾸지않을 때 사용

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // [1] null 값을 안정적으로 처리하는 방법
        var name:String = "jhon"
        // 1. nullable - null 허용
        var age:Int? = null
        var ac:Activity? = null // 메모리에 존재하고 있는 null을 쳐다봄

        // 2. ? : safeCall
        //age.plus(3) // null Point Exception 발생 -> 방지하기 위해 나온게 nullSafety
        var result = age?.plus(3)

        //3. 엘비스 익스프레스 ?: 기본값 지정
        var result2 = age ?: 55 //기본값을 셋팅해서 null 안정성 생성
        var result3 = result2.minus(45)

        Log.d("result222", "result3 = $result3" )
    }

    // [3] 스코프 함수
    // run let apply also
    // with
    // 1. run
    fun stuRun() {
        val nums = mutableListOf("010-1234-5678","010-2222-1111","010-7894-8888")
        val names = mutableListOf("jenee","jisoo","rose")
        val list = mutableListOf(1,2,3,4,5,6,7,8,9)

        names.run {
            names.add("YG") 
            add("lisa")// 스코프 안에서는 names. 생략 가능
        }

        val blackPink = BlackPink()
        blackPink.specs.add(Spec("lisa","g","g"))
        blackPink.specs.add(Spec("lisa","g","g"))
        blackPink.specs.add(Spec("lisa","g","g"))
        // 귀찮음... 스코프 사용

        // 반환 타입이 서로 달라서 같은게 두개씩 있음
        // 1. run = 몇개를 넣든 마지막 값만 리턴
        val resultRun = blackPink.specs.run {
            add(Spec("lisa","g","g"))
        }
        Log.d("result222", "resultRun = $resultRun" )

        // 2. let : {} 안에서 누굴 부르는지 명시해줘야함
        // = 몇개를 넣든 마지막 값만 리턴 
        val resultLet = blackPink.specs.let { bp->
            bp.add(Spec("lisa","g","g"))
        }
        Log.d("result222", "resultLet = $resultLet" )

        // 3. apply
        // 변수에 반영된 값 부를때
        val resultApply = blackPink.specs.apply {
            add(Spec("lisa","g","g"))
        }
        Log.d("result222", "resultApply = $resultApply" )

        // 4. also
        // 변수에 반영된 값 부를때
        val resultAlso = blackPink.specs.also { bp->
            bp.add(Spec("lisa","g","g"))
        }
        Log.d("result222", "resultAlso = $resultAlso" )




    }

}

class BlackPink {
    var specs = mutableListOf<Spec>()

    init {
//        specs.add(Spec("jenee","g","g"))
//        specs.add(Spec("jisoo","g","b"))
//        specs.add(Spec("rose","g","b"))
    }
}

data class Spec (
    var name:String = "",
    var song:String = "",
    var rap:String = "",
)


class Person {
    var name = ""
    var age = ""
}