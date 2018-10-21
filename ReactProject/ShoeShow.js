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
    TouchableHighlight,
    ToolbarAndroid,
    Alert,
    Image,
} from 'react-native';
import { Navigator } from 'react-native-deprecated-custom-components';
import TabNavigator from 'react-native-tab-navigator';

import CartShow from './CartShow';
import OrderShow from './OrderShow';
import GoodClassify from './GoodClassify';
import my from './my';
/**
 * 获取设备屏幕的宽和高
 */
var { height, width } = Dimensions.get('window');
/**
 * 从json文件中获取数据
 */
var data = require('./Shoe.json');
/**
 * 写一个数组用来克服,本地图片不能使用变量来添加的问题,,笨方法
 */
var num = [];
// const img_arr = [require('./res/image/good1_icon.jpg'),require('./res/image/good2_icon.jpg'),require('./res/image/good3_icon.jpg'),
// require('./res/image/good4_icon.jpg'),require('./res/image/good5_icon.jpg')]
const img_arr = [require('./res/shoeImage/shoe1.jpg'), require('./res/shoeImage/shoe2.jpg'), require('./res/shoeImage/shoe3.jpg'),
require('./res/shoeImage/shoe4.jpg'), require('./res/shoeImage/shoe5.jpg'), require('./res/shoeImage/shoe6.jpg'),
require('./res/shoeImage/shoe7.jpg'), require('./res/shoeImage/shoe8.jpg'), require('./res/shoeImage/shoe9.jpg')]

/**
 * 创建一个购物车的变量数组,用来存放id
 */
var cart = [];
/**
 * 必须加 default 要不然这个页面跳不过来 ,报错
 */
export default class ShoeShow extends Component {
    constructor(props) {
        super(props);
        var ds = new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 });
        this.state = {
            //将json中的数据导入进来
            dataSource: ds.cloneWithRows(data),
            selectedTab: 'GoodsShow'
        }
    }
    render() {
        num = [0, 1, 2, 3, 4, 5, 6, 7, 8];
        if (this.props.cart !== []) {
            //复制数组
            cart = this.props.cart.concat();
        }
        cart = this.props.cart;

        return (
            <View style={styles.container}>
                <ToolbarAndroid
                    style={styles.toolbar}
                    navIcon={require('./res/image/back_icon.png')}
                    title='零食商品'
                    //    subtitle='商品列表'
                    actions={[{ title: '商品列表' },
                    { title: '订单列表' },
                    { title: '购物车' }]}
                    // 使用这个将坐标传过去了.在函数中用参数接受就可以了
                    onActionSelected={this.toolBaronActionSelected.bind(this)}
                    onIconClicked={this.backToBefore.bind(this)}
                />
                <ListView dataSource={this.state.dataSource}
                    /**
                     * 强烈说明一下,如果是在listView中使用TouchableOpacity
                     * 那么就有必要在renderRow中使用bind(this) 进行绑定this,
                     * 并且在TouchableOpacity中的onPress中写函数的时候要使用ES6的语法 () => this.functionName(...params))
                     *
                     */
                    renderRow={this.renderRow.bind(this)}

                ></ListView>

                <TabNavigator >
                    <TabNavigator.Item
                        selected={this.state.selectedTab === 'GoodClassify'}
                        title="首页"
                        //选中时的title的样式
                        selectedTitleStyle={{ color: "#007aff" }}//设置tab标题颜色
                        renderIcon={() => <Image style={styles.icon} source={require('./res/icon/index20.jpg')} />}
                        renderSelectedIcon={() => <Image style={[styles.icon, { tintColor: '#007aff' }]} source={require('./res/icon/index20.jpg')} />}//设置图标选中颜色
                        // badgeText="1"
                        onPress={() => this._navigateClassify(cart)}>
                        <Text></Text>
                    </TabNavigator.Item>
                    <TabNavigator.Item
                        selected={this.state.selectedTab === 'CartShow'}
                        title="购物车"
                        //选中时的title的样式
                        selectedTitleStyle={{ color: "#007aff" }}//设置tab标题颜色
                        renderIcon={() => <Image style={styles.icon} source={require('./res/icon/cart20px.jpg')} />}
                        renderSelectedIcon={() => <Image style={[styles.icon, { tintColor: '#007aff' }]} source={require('./res/icon/index20.jpg')} />}//设置图标选中颜色
                        // badgeText="1"
                        onPress={() => this._navigate2(cart)}>
                        <Text></Text>
                    </TabNavigator.Item>
                    <TabNavigator.Item
                        selected={this.state.selectedTab === 'OrderShow'}
                        title="订单"
                        //选中时的title的样式
                        selectedTitleStyle={{ color: "#007aff" }}//设置tab标题颜色
                        renderIcon={() => <Image style={styles.icon} source={require('./res/icon/order20.jpg')} />}
                        renderSelectedIcon={() => <Image style={[styles.icon, { tintColor: '#007aff' }]} source={require('./res/icon/order20.jpg')} />}//设置图标选中颜色
                        // badgeText="1"
                        onPress={() => this._navigate1(cart)}>
                        <Text></Text>
                    </TabNavigator.Item>
                    <TabNavigator.Item
                        selected={this.state.selectedTab === 'my'}
                        title="我的"
                        //选中时的title的样式
                        selectedTitleStyle={{ color: "#007aff" }}//设置tab标题颜色
                        renderIcon={() => <Image style={styles.icon} source={require('./res/icon/wode20.jpg')} />}
                        renderSelectedIcon={() => <Image style={[styles.icon, { tintColor: '#007aff' }]} source={require('./res/icon/wode20.jpg')} />}//设置图标选中颜色
                        // badgeText="1"
                        onPress={() => this._navigate3(cart)}>
                        <Text></Text>
                    </TabNavigator.Item>
                </TabNavigator>

            </View>
        );
    }
    _navigate3(cart, type = 'Normal') {
        /**
         * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
         */

        this.props.navigator.push({
            component: my,
            passProps: {
                cart: cart
            },
            type: type
        })
    }
    // 返回一个Item
    renderRow(rowData) {

        return (
            <View style={styles.itemStyle}>
                <Image source={img_arr[num.shift()]} style={styles.imageStyle} />

                <View style={{ flexDirection: 'column' }}>
                    <Text style={{ marginTop: 5, fontSize: 17 }}>{rowData.title}</Text>
                    <Text style={{ marginBottom: 5, fontSize: 13, color: 'orange' }}>{rowData.intro}</Text>
                    <Text style={{ marginBottom: 5, fontSize: 13, color: 'red' }}>{rowData.price}</Text>
                </View>
                <View style={[styles.addButtonView]}>
                    <View style={{ alignSelf: 'center' }}>
                        {/*
                * 测试添加之后cart中是不是有值
                */ }
                        {/* <TouchableOpacity 
                  activeOpacity={0.5}//点击时的透明度
                  style={styles.buttonAdd}
                  onPress={() => this.showCart() }
                  >
                    <Text style={{fontSize:16}}>
                        test
                    </Text>
                 </TouchableOpacity> */}
                        <TouchableOpacity
                            activeOpacity={0.5}//点击时的透明度
                            style={styles.buttonAdd}
                            onPress={() => this.GoodsAdd(rowData.id, rowData.title)}
                        >
                            <Text style={{ fontSize: 16 }}>
                                添加购物车
                    </Text>
                        </TouchableOpacity>
                    </View>
                </View>

            </View>
        );
    }

    GoodsAdd(id, title) {
        cart.push(id);
        Alert.alert('添加成功', title + '');
    }
    /**
     * 测试添加之后cart中是不是有值
     */
    // showCart() {
    //     Alert.alert(cart + '');
    // }
    //position 坐标 0,1,2 三个
    /**
     * 导航栏右侧的跳转
     */
    toolBaronActionSelected(position) {

        if (position == 0) {
            this._navigateClassify(cart);
        }
        if (position == 2) {
            this._navigate2(cart);
        }
        if (position == 1) {
            this._navigate1(cart);
        }
    }
    /**
     * 如果是写在一个函数里,调用这个函数的时候要使用一个回调bind
     * 返回上一页
     */
    backToBefore() {
        this.props.navigator.pop();
    }
    _navigateClassify(cart, type = 'Normal') {
        /**
         * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
         */
        this.props.navigator.push({
            component: GoodClassify,
            passProps: {
                cart: cart
            },
            type: type
        })
    }
    /**
* 给Navigator传递参数.
* @param name 参数
* @private 
* type 有值表示如果没有值传过来的话就使用这个默认的值,有的话就替换为传过来的值
*/
    _navigate2(cart, type = 'Buttom') {
        /**
         * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
         */
        this.props.navigator.push({
            component: CartShow,
            passProps: {
                cart: cart
            },
            type: type
        })
    }
    _navigate0(cart, type = 'Normal') {
        /**
         * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
         */
        this.props.navigator.push({
            component: GoodsShow,
            passProps: {
                cart: cart
            },
            type: type
        })
    }
    /**
     * 跳转订单页
     * @param {*} cart 
     * @param {*} type 
     */
    _navigate1(cart, type = 'Normal') {
        /**
         * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
         */
        this.props.navigator.push({
            component: OrderShow,
            passProps: {
                cart: cart
            },
            type: type
        })
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    toolbar: {
        width: width,
        height: 60,
        backgroundColor: 'skyblue',
        marginTop: 0,
    },
    itemStyle: {
        // 主轴方向
        flexDirection: 'row',
        // 下边框
        borderBottomWidth: 1,
        borderBottomColor: 'gray',
    },
    imageStyle: {
        // 尺寸
        width: 72,
        height: 72,
        // 边距
        marginLeft: 10,
        margin: 10
    },
    addButtonView: {
        flex: 1,
        flexDirection: 'row-reverse',
    }
    ,
    buttonAdd: {
        backgroundColor: 'skyblue',

        width: 80,
        height: 20,
    },
    icon: {
        width: 20,
        height: 20
    }

}); 