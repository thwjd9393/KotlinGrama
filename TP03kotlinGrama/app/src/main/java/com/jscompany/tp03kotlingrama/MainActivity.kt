package com.jscompany.tp03kotlingrama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // var과 val의 차이
        var myName = "홍길동"
        myName = "홍길동2"
        val PIE = 3.14
        //pie = 3.141592 - 재 정의 불가
        // val는 상수 바꿀 수 없는 값
        // 상수는 대문자로 선언
        Log.d(TAG, "myName = "+myName)

        for( idx in 0..10) {
            Log.d(TAG, "$idx")
        }

        //컬렉션
        //ex) 게시판
        // 리스트와 뮤터블리스트
        // 리스트는 값 변경 불가
        // 뮤터블리스트 값 변경 가능
        val list = mutableListOf<Int>() //값의 타입을 명시 - 제네릭
        //값 넣기
        list.add(5)
        list.add(10)
        list.add(55)
        list.add(54)

        Log.d("컬렉션", "${list.get(2)}")

        //맵 - key와 value 형태
        var mutableMap = mutableMapOf<String,String>()
        mutableMap.put("1", "aaa")
        mutableMap.put("2", "bbb")
        mutableMap.put("3", "xcx")

        Log.d(TAG,"map = ${mutableMap.get("2")}")

        // 힘수
        // 함수 사용하는 용도
        // 코드를 분류하기 위함
        firstFun()
        funParam("하하")

        //클래스 사용 방법
        // 1. 초기화
        var cls = 클래스() //초기화를 한 후 사용
        // 초기화 => 인스턴스화
        // 인스턴스 => 메모리에 로드되어 있는 클래스

        cls.aa
        cls.bbb()

        val log = Log()
        log.d("ㅎㅎ","ㄱㄱ")

        //2. companion object로 만들기
        Log2.aaa

        val child = Child()
        child.showMoney()
        child.showHouse()
        val parent = Parent()
        parent.showHouse()

        var son = Son()
        var aa = son.getNum()

    }///

    //함수
    //기본함수
    fun firstFun() {
        //코드입력
        Log.d(TAG,"안녕")
    }

    //입력값이 있는 함수
    fun funParam(param:String) {
        Log.d(TAG,"$param")
    }

    //출력값이 있는 함수
    fun funOutput() :String {
        return "오예"
    }

}

// 클래스란?
// 변수와 함수의 모음
class 클래스 {

    init {
        //클래스를 초기화하며 호출
    }

    var aa : String = "" // 변수 - 프로퍼티

    fun bbb() {   // 함수 - 메서드
        var car = "suv"
    }
}

class Log {
    fun d(param1: String, param2: String) {
        print("${param1} : ${param2}")
    }
}

class Log2 {

    companion object {
        var aaa = "companion object 클래스 초기화 없이 사용하는 키워드"
        fun d(param1: String, param2: String) {
            print("${param1} : ${param2}")
        }
    }
    
}

//상속 사용하는 이유
// 1.기존 작성된 코드 재사용하기 위해
// 2.코드를 재활용하는데 -> 체계적인 구조로 사용하기 위해서
open class Parent { // 상속 가능하게 하기 위해 [open]키워드
    var money = 50000
    open var house = "강남" //오버라이드 가능하기 위해 open 키워드 필요

    open fun showHouse() {
        Log.d("MainActivity","showHouse = ${house}")
    }
}

class Child : Parent() { //상속받은 값 사용하기 위해선 초기화 필요 () 붙임
    override var house = "분당"
    
    fun showMoney() {
        //상속 받으면 부모의 것을 내것처럼 사용한다
        Log.d("MainActivity","money = ${money}")
    }

    //override : 상속관계에서 부모의 메소드를 재사용
    override fun showHouse() {
        Log.d("MainActivity","showHouse = ${house}")
    }
}


class Son {
    fun getNum() : Int {
        return 1
    }

    //오버로드 = 메소드명이 같은데 파라미터 타입이나 개수가 다른 것
    fun getNum(two : String) : Int {
        return 2
    }

    fun getNum(two : Int) : Int {
        return 2
    }
}

