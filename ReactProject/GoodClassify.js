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
    import {Navigator} from 'react-native-deprecated-custom-components';
    import TabNavigator from 'react-native-tab-navigator';
    /**
     * 导入另一个js文件 ,用来实现页面的跳转
     */
    import GoodsShow from './GoodsShow';
    import CartShow from './CartShow';
    import OrderShow from './OrderShow';
import ComputerShow from './ComputerShow';
import ShoeShow from './ShoeShow';
import ClothesShow from './ClothesShow';
import my from './my';
    var cart = [];
    /**
     * 获取设备屏幕的宽和高
     */
    var {height,width} = Dimensions.get('window');
    export default class GoodClassify extends Component {
        constructor(props) {
            super(props);
            this.state= {
                selectedTab: 'GoodClassify'
            }
        }
        render() {
            return (
                <View  style={styles.container}>
                    <ToolbarAndroid 
                style={[styles.toolbar]}
                navIcon={require('./image/back_icon.png')} 
                title='商品分类'
                //    subtitle='商品列表'
                actions={[{title: '商品列表' },
                {title: '订单列表'} ,
                {title: '购物车'}]}
            // 使用这个将坐标传过去了.在函数中用参数接受就可以了
            onActionSelected={this.toolBaronActionSelected.bind(this)}
            onIconClicked={this.backToBefore.bind(this)}
                />   
               
                <View style={{position:'relative' , left:56,top:80 , flexDirection: 'row'}}>
                <View style={[{justifyContent: 'center' , alignItems: 'center'}]}>
                <TouchableOpacity
                        activeOpacity={0.5}//点击时的透明度
                        
                        //点击事件，要记得绑定
                        // onPress={this._navigate.bind(this)}
                        onPress={() => this._goToAnother(1)}>
                <Image style={[styles.imageStyle]}
                source={require('./classifyImage/clothClassify_icon.jpg')}>
                </Image>
                <Text>  零食</Text>
                </TouchableOpacity>
                </View>
                <View style={[{justifyContent: 'center' , alignItems: 'center'}]}>
                <TouchableOpacity
                        activeOpacity={0.5}//点击时的透明度
                        
                        //点击事件，要记得绑定
                        // onPress={this._navigate.bind(this)}
                        onPress={() => this._goToAnother(2)}>
                <Image style={[styles.imageStyle, {marginLeft:50}]}
                source={require('./classifyImage/clothClassify_icon.jpg')}>
                </Image>
                <Text style={[ {marginLeft:50}]}>  衣服</Text>
                </TouchableOpacity>
                </View>
            </View>
            <View style={{position:'relative' , left:56,top: 200 , flexDirection: 'row'}}>
            
                <View style={[{justifyContent: 'center' , alignItems: 'center'}]}>
                <TouchableOpacity
                        activeOpacity={0.5}//点击时的透明度
                        
                        //点击事件，要记得绑定
                        // onPress={this._navigate.bind(this)}
                        onPress={() => this._goToAnother(3)}>
                <Image style={[styles.imageStyle]}
                source={require('./classifyImage/computerClassify_icon.jpg')}>
                </Image>
                <Text>  电脑</Text>
                </TouchableOpacity>
                </View>
                <View style={[{justifyContent: 'center' , alignItems: 'center'}]}>
                <TouchableOpacity
                        activeOpacity={0.5}//点击时的透明度
                        
                        //点击事件，要记得绑定
                        // onPress={this._navigate.bind(this)}
                        onPress={() => this._goToAnother(4)}>
                <Image style={[styles.imageStyle, {marginLeft:50}]}
                source={require('./classifyImage/shoeClassify_icon.jpg')}>
                </Image>
                <Text style={[ {marginLeft:50}]}>  鞋子</Text>
                </TouchableOpacity>
                </View>
            </View>
                <TabNavigator>
                    {this.tabNavigator1('GoodClassify', require('./icon/index20.jpg') , '首页',0)}
                    {this.tabNavigator1('CartShow', require('./icon/cart20px.jpg'), '购物车',2)}
                    {this.tabNavigator1('OrderShow', require('./icon/order20.jpg'),'订单',1)}
                    {this.tabNavigator1('my', require('./icon/wode20.jpg') , '我的',3)}
                </TabNavigator>
                </View>
            );
        }
        tabNavigator1(selectTable , iconUrl , title ,index ) {
            return (

                    <TabNavigator.Item
                            selected={this.state.selectedTab === selectTable }
                            title={title}
                            //选中时的title的样式
                            selectedTitleStyle={{color:"#007aff"}}//设置tab标题颜色
                            renderIcon={() => <Image style={styles.icon} source={iconUrl} />}
                            renderSelectedIcon={() => <Image style={[styles.icon,{tintColor:'#007aff'}]} source={ iconUrl } />}//设置图标选中颜色
                            // badgeText="1"
                            onPress={() => this.toolBaronActionSelected(index)}>
                            <Text></Text>
                        </TabNavigator.Item>
            );
        }
        /**
         * 用来进行跳转
         */
        _goToAnother(position) {
            if(position === 1) {
                this._navigateClassify(GoodsShow ,cart);
            }
            if(position === 2) {
                this._navigateClassify( ClothesShow ,cart);
            }

            if(position === 3) {
                this._navigateClassify( ComputerShow ,cart);
            }
            if(position === 4) {
                this._navigateClassify( ShoeShow,cart );
            }
        }
        //position 坐标 0,1,2 三个
        /**
         * 导航栏右侧的跳转
         */
        toolBaronActionSelected(position) {
        
            if(position === 0) {
            this._navigate0();
            }
            if( position === 2) {
                this._navigate2(cart);
            }
            if(position === 1) {
                this._navigate1(cart);
            }
            if( position === 3) {
                this._navigate3(cart)
            }
    }
    _navigate3(cart,type = 'Normal') {
        /**
         * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
         */
        
            this.props.navigator.push({
              component: my,
              passProps: {
                cart:cart
              },
              type: type
            })
          }
    _navigateClassify(name,cart,type = 'Normal') {
        /**
         * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
         */
            // alert(cart)
            this.props.navigator.push({
            component: name,
            passProps: {
                cart:cart
            },
            type: type
            })
        }

    /**
        * 跳转到Order的界面
        */
    _navigateOrder(cart,type = 'Normal') {
        /**
         * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
         */
        
            this.props.navigator.push({
            component: OrderShow,
            passProps: {
                cart:cart
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
    _navigate2(cart,type = 'Normal') {
    /**
        * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
        */
    
        this.props.navigator.push({
            component: CartShow,
            passProps: {
            cart:cart
            },
            type: type
        })
        }
        _navigate0(cart,type = 'Normal') {
        /**
            * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
            */
            this.props.navigator.push({
                component: GoodsShow,
                passProps: {
                cart:cart
                },
                type: type
            })
            }
    /**
        * 跳转订单页
        * @param {*} cart 
        * @param {*} type 
        */
            _navigate1(cart,type = 'Normal') {
            /**
                * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
                */
                this.props.navigator.push({
                    component: OrderShow,
                    passProps: {
                    cart:cart
                    },
                    type: type
                })
                }
                
    }
    const styles = StyleSheet.create ({
        container: {
            flex: 1,
        
        },
        toolbar: {
            width: width,
            height: 60,
            backgroundColor: 'skyblue',
            marginTop: 0,
            
        },
        textByGoodClassify: {
            fontSize: 26,
            color: 'blue',
            // fontStyle: 'italic',
        }
        ,
        itemStyle: {
            // 主轴方向
            flexDirection:'row',
            // 下边框
            borderBottomWidth:1,
            borderBottomColor:'gray',
        },
        imageStyle: {
            // 尺寸
            width:130,
            height:130,
            // 边距
            
            backgroundColor: 'red'
        },
        addButtonView: {
            flex:1,
            flexDirection: 'row-reverse',
        }
        ,
        buttonAdd: {
            backgroundColor: 'skyblue',
            width:80,
            height:20,
        },
        icon:{
            width:20,
            height:20
        }

    }); 