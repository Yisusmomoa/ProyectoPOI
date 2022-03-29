package com.example.POI.ModelClasses

class Users {
    private var uid:String=""
    private var username:String=""
    private var profile:String=""
    private var status:String=""
    private var search:String=""

    constructor()
    constructor(uid: String, username: String, profile: String,
                status: String, search: String) {
        this.uid = uid
        this.username = username
        this.profile = profile
        this.status = status
        this.search = search
    }

    fun getUID():String?{
        return uid
    }
    fun setUID(uid:String){
        this.uid=uid
    }

    fun getUsername():String?{
        return  username
    }
    fun setUsername(username: String){
        this.username=username
    }

    fun getProfile():String{
        return profile
    }
    fun setProfile(profile:String){
        this.profile=profile
    }

    fun getStatus():String{
        return status
    }
    fun setStatus(status: String){
        this.status=status
    }

    fun getSearch():String{
        return  search
    }
    fun setSearch(search: String){
        this.search=search
    }

}