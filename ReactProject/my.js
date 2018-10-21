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
    BackHandler,
    TouchableOpacity,
    TouchableHighlight,
    ToolbarAndroid,
    Alert,
    Image,
    ToastAndroid,
} from 'react-native';
import { Navigator } from 'react-native-deprecated-custom-components';
import TabNavigator from 'react-native-tab-navigator';
import RNExitApp from 'react-native-exit-app';
import CartShow from './CartShow';
import OrderShow from './OrderShow';
import GoodClassify from './GoodClassify';

var cart = []
/**
 * 获取设备屏幕的宽和高
 */
var { height, width } = Dimensions.get('window');
var firstClick = 0;
export default class my extends Component {

    constructor(props) {
        super(props);
        this.handleBack = this.handleBack.bind(this);
        this.state = {

            selectedTab: 'my',
        }
    }
    //组件加载之后添加监听
    componentDidMount() {
        //监听返回键的点击,   点击了只有执行相应的回调函数  
        BackHandler.addEventListener('hardwareBackPress', this.handleBack)
    }
    //组件卸载之前移除监听
    componentWillUnmount() {
        BackHandler.removeEventListener('hardwareBackPress', this.handleBack)
    }

    handleBack() {
        var timestamp = (new Date()).valueOf();
        if (timestamp - firstClick > 2000) {
            firstClick = timestamp;
            ToastAndroid.show('再按一次退出', ToastAndroid.SHORT);
            return true;
        } else {
            return false;
        }
    }
    render() {
        return (
            <View style={styles.container}>

                <ToolbarAndroid
                    style={styles.toolbar}
                    navIcon={require('./res/image/back_icon.png')}
                    title='订单'
                    //    subtitle='商品列表'
                    actions={[{ title: '商品列表' },
                    { title: '订单列表' },
                    { title: '购物车' }]}
                    // 使用这个将坐标传过去了.在函数中用参数接受就可以了
                    onActionSelected={this.toolBaronActionSelected.bind(this)}
                    onIconClicked={this.backToBefore.bind(this)}
                />
                <View>
                    <Text style={styles.buttonText}>
                        用户123:
                    </Text>
                </View>
                <View>
                    <TouchableOpacity
                        activeOpacity={0.5}//点击时的透明度
                        style={styles.buttonAdd}
                        onPress={() => this._navigate2(cart)}
                    >
                        <Text style={styles.buttonText}>
                            我的购物车
                </Text>
                    </TouchableOpacity>
                </View>
                <View>
                    <TouchableOpacity
                        activeOpacity={0.5}//点击时的透明度
                        style={styles.buttonAdd}
                        onPress={() => this._navigate1(cart)}
                    >
                        <Text style={styles.buttonText}>
                            订单
                </Text>
                    </TouchableOpacity>
                </View>

                <TabNavigator>
                    {this.tabNavigator('GoodClassify', require('./res/icon/index20.jpg'), '首页', 0)}
                    {this.tabNavigator('CartShow', require('./res/icon/cart20px.jpg'), '购物车', 2)}
                    {this.tabNavigator('OrderShow', require('./res/icon/order20.jpg'), '订单', 1)}
                    {this.tabNavigator('my', require('./res/icon/wode20.jpg'), '我的', 3)}
                </TabNavigator>
            </View>
        );
    }
    tabNavigator(selectTable, iconUrl, title, index) {
        return (

            <TabNavigator.Item
                selected={this.state.selectedTab === selectTable}
                title={title}
                //选中时的title的样式
                selectedTitleStyle={{ color: "#007aff" }}//设置tab标题颜色
                renderIcon={() => <Image style={styles.icon} source={iconUrl} />}
                renderSelectedIcon={() => <Image style={[styles.icon, { tintColor: '#007aff' }]} source={iconUrl} />}//设置图标选中颜色
                // badgeText="1"
                onPress={() => this.toolBaronActionSelected(index)}>
                <Text></Text>
            </TabNavigator.Item>
        );
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


    //position 坐标 0,1,2 三个
    /**
     * 导航栏右侧的跳转
     */
    toolBaronActionSelected(position) {

        if (position == 0) {
            this._navigateClassify(cart);
        }
        if (position == 2) {
            this._navigate2(this.props.cart);
        }
        if (position == 1) {
            this._navigate1(this.props.cart);
        }
        if (position == 3) {
            this._navigate3(this.props.cart);
        }
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
    /**
     * 如果是写在一个函数里,调用这个函数的时候要使用一个回调bind
     * 返回上一页
     */
    backToBefore() {
        this.props.navigator.pop();
    }
    /**
* 给Navigator传递参数.
* @param name 参数
* @private 
* type 有值表示如果没有值传过来的话就使用这个默认的值,有的话就替换为传过来的值
*/
    _navigate2(cart, type = 'Normal') {
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
            component: GoodClassify,
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
    buttonAdd: {
        backgroundColor: 'skyblue',

    },
    buttonText: {
        fontSize: 29,
        color: 'black',

    },
    toolbar: {
        width: width,
        height: 60,
        backgroundColor: 'skyblue',
        marginTop: 0,
    },
})