import React, { Component } from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View,
    ListView,
    ScrollView,
    Button,
    Dimensions,
    TextInput,
    TouchableOpacity,
    Alert
} from 'react-native';

import { Navigator } from 'react-native-deprecated-custom-components';
// import {Dimensions} from 'react-native';
/**
 * 导入另一个js文件 ,用来实现页面的跳转
 */
import GoodsShow from './GoodsShow';
import CartShow from './CartShow';
import GoodClassify from './GoodClassify';

/**
 * 获取设备屏幕的宽和高
 */
var { height, width } = Dimensions.get('window');
export default class LoginPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            containerWidth: width,
            containerHeight: height,
            pass_text: '',
            user_text: '',

        }

    }

    render() {
        return (
            <View style={[styles.container, { width: this.state.containerWidth, height: this.state.containerHeight }]} >
                <View style={[styles.head, { position: 'relative' }]}>
                    <Text style={styles.title}>
                        购物商城
                    </Text>
                </View>
                <View style={[styles.loginContainer, { position: 'relative' }]}>
                    <Text style={styles.login}>
                        请登录
                </Text>
                </View>
                <View style={[styles.inputInfo, { position: 'relative' }]}>
                    <Text style={styles.textBeforInput} >账号:</Text>
                    <TextInput placeholder='请输入账号' style={styles.textInputInfo} clearButtonMode='always'
                        onChangeText={(user_text) => this.setState({ user_text })}>
                    </TextInput>
                </View>
                <View style={[styles.inputInfo, { position: 'relative' }]}>
                    <Text style={styles.textBeforInput} >密码:</Text>
                    {/* clearButtonMode消除按钮 autoFocus 自动聚焦 maxLength输入的最大长度,多了就不在录入 secureTextEntry文本框遮住之前输入的文字,类似密码
                onChangeText={(pass_text) => this.setState({pass_text})} 表示当文本变化的时候就设置state的值
                */}
                    <TextInput placeholder='请输入密码' style={styles.textInputInfo} clearButtonMode='always' maxLength={8} secureTextEntry={true}
                        onChangeText={(pass_text) => this.setState({ pass_text })}>

                    </TextInput>
                </View>
                <View style={[styles.inputInfo, { position: 'relative' }]}>
                    <TouchableOpacity
                        activeOpacity={0.5}//点击时的透明度
                        style={styles.submitButton}
                        //点击事件，要记得绑定
                        // onPress={this._navigate.bind(this)}
                        onPress={this._login.bind(this)}
                    >
                        <Text style={{ fontSize: 15, color: 'white', fontWeight: 'bold' }}>
                            登录
                            </Text>
                    </TouchableOpacity>
                </View>
            </View>
        );
    }
    _login() {
        var temp;
        var paramsData = new FormData();
        paramsData.append('name', this.state.user_text);
        paramsData.append('passwd', this.state.pass_text);
        fetch('http://39.106.191.144:8180/loginGo1', {
            method: 'POST',
            // headers: {
            //    'Accept':'application/json'
            // },
            body: paramsData
        })
            .then((response) => {
                // alert(response.status +'' ) 
                //    alert( response.json() )
                return response.json()
            })
            .then((jsonData) => {
                // alert(jsonData.result);
                temp = jsonData.result;
                /**
* 给Navigator传递参数.
* @param name 参数
* @private 
* type 有值表示如果没有值传过来的话就使用这个默认的值,有的话就替换为传过来的值
*/

                /**
                 * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
                 */
                if (temp == 1) {
                    Alert.alert('欢迎登录', this.state.user_text);
                    this.props.navigator.push({
                        component: GoodClassify,
                    })
                } else {
                    Alert.alert('用户名和密码错误')
                }
            });

    }




    //   post() {
    //     fetch('http://ip.taobao.com/service/getIpInfo.php', {
    //         method: 'POST',//1
    //         headers: {
    //             'Content-Type': 'application/json',
    //         },
    //         body: JSON.stringify({//2
    //             'ip': '59.108.23.12'
    //         })
    //     }).then((response) => response.json())
    //         .then((jsonData) => {
    //             let country = jsonData.data.country;
    //             let city = jsonData.data.city;
    //             alert("country:" + country + "-------city:" + city);
    //         });
    // }
    // get() {
    //     fetch('http://ip.taobao.com/service/getIpInfo.php?ip=59.108.23.12', {
    //         method: 'GET',
    //         headers: {
    //             'Content-Type': 'application/json'
    //         }
    //     }).then((response) => response.json())//1
    //         .then((jsonData) => {//2
    //             let country = jsonData.data.country;
    //             let city = jsonData.data.city;
    //             alert("country:" + country + "-------city:" + city);
    //         });
    // }


}

const styles = StyleSheet.create({
    head: {

        width: 411,
        height: 60,

    },
    title: {
        fontSize: 30,
        color: 'blue',
        alignSelf: 'center', //使本身的元素居中, 想让谁居中,就用谁
    },
    container: {
        backgroundColor: 'white',
        //子元素的排列方式, row column row-reverse column-reverse
        flexDirection: 'column',
        //这个是如何分配顺着父容器主轴的弹性（flex）元素之间及其周围的空间
        justifyContent: 'center',
    },
    loginContainer: {
        marginTop: 10,
        backgroundColor: 'white',

    },
    login: {
        fontSize: 24,
        color: 'black',
        alignSelf: 'center', //使本身的元素居中, 想让谁居中,就用谁
    },
    inputInfo: {
        //margin使用的是和上一盒子的相对定位
        marginTop: 15,
        backgroundColor: 'white',
        flexDirection: 'row',
        alignSelf: 'center',

    },
    textInputInfo: {
        width: 200,
        height: 50,
        backgroundColor: 'white',
        alignSelf: 'center', //使本身的元素居中, 想让谁居中,就用谁
    },
    textBeforInput: {
        color: 'black',
        alignSelf: 'center', //使本身的元素居中, 想让谁居中,就用谁
    },
    submitButton: {
        //按钮圆角
        borderRadius: 20,
        backgroundColor: 'skyblue',
        height: 35,
        width: width / 2,
        //在侧轴上的方向上对弹性元素对其,和justifyContent 相同但是方向不一样
        alignItems: 'center',
        justifyContent: 'center',
    },
});