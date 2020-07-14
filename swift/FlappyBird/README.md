### FlyBird

### 1：物体的添加

#### 1.1：游戏的状态

​	游戏分为三个状态：初始化，游戏中，结束。

```swift
enum GameStatus {
  case idle    //初始化
  case running    //游戏运行中
  case over    //游戏结束
}
```

#### 1.2：添加节点

​	页面刚加载的时候是初始化状态，需要想页面中添加`Bird`节点和地板节点

```swift
var floor1: SKSpriteNode!
var floor2: SKSpriteNode!
var bird: SKSpriteNode!

//表示当前游戏状态的变量，初始值为初始化状态
var gameStatus: GameStatus = .idle

override func didMove(to view: SKView) {
  self.backgroundColor = SKColor(red: 80.0/255.0, green: 192.0/255.0, blue: 203.0/255.0, alpha: 1.0)
  // 设置地面
  setFloor()
  // 添加小鸟
  setBird()
  //初始化游戏
  shuffle()
}
```

- 添加小鸟

```swift
func setBird() {
  bird = SKSpriteNode(imageNamed: "player1")
  addChild(bird)
}
```

- 添加地板

```swift
func setFloor() {
  floor1 = SKSpriteNode(imageNamed: "floor")
  //设置锚点
  floor1.anchorPoint = CGPoint(x: 0, y: 0)
  floor1.position = CGPoint(x: 0, y: 0)
  addChild(floor1)

  floor2 = SKSpriteNode(imageNamed: "floor")
  floor2.anchorPoint = CGPoint(x: 0, y: 0)
  floor2.position = CGPoint(x: floor1.size.width, y: 0)
  addChild(floor2)
}
```

- 初始化游戏

```swift
//游戏初始化
func shuffle()  {
  gameStatus = .idle
  bird.position = CGPoint(x: self.size.width * 0.5, y: self.size.height * 0.5)
  birdStartFly()
}
```

#### 1.3：小鸟飞行与地板移动

​	游戏初始化后，我们应该给小鸟添加飞翔的动作，并且地板可以无限循环

- 给小鸟添加飞翔动作

    这里小鸟的飞翔是由三张图片循环播放的效果，我们需要做的就是给小鸟添加一个动作，这个动作就是播放顺序播放这三张图片，一直循环播放

```swift
// 初始化小鸟起飞
func birdStartFly() {
  let action = SKAction.animate(with: [SKTexture(imageNamed: "player1"),SKTexture(imageNamed: "player2"), SKTexture(imageNamed: "player3")], timePerFrame: 0.15)
  bird.run(SKAction.repeatForever(action), withKey: "fly")
}
```

- 接下来就是地板的循环移动

    由于地板的循环移动是：两张图片位置关系的转换

    当第一个地板移出界面的时候，第二张图片此时正好填充了整个界面；所以我们应该判断第一个地板移出界面的横坐标与他的宽度之间的关系，依次经过判断来将第一个地板的位置移动到第二个地板之后；同理，对于第二个地板也是如此

```swift
// 移动地面
func moveScence() {
  floor1.position = CGPoint(x: floor1.position.x-1, y: floor1.position.y)
  floor2.position = CGPoint(x: floor2.position.x-1, y: floor2.position.y)
  if floor1.position.x < -floor1.size.width {
    floor1.position = CGPoint(x: floor2.position.x + floor2.size.width, y: floor1.position.y)
  }

  if floor2.position.x < -floor2.size.width {
    floor2.position = CGPoint(x: floor1.position.x + floor1.size.width, y: floor2.position.y)
  }

}
```

- 然后我们将移动地面的动画放入到`update`方法中，每次刷新页面的时候都调用

> update()方法为SKScene自带的系统方法，在画面每一帧刷新的时候就会调用一次

```swift
override func update(_ currentTime: TimeInterval) {
  // Called before each frame is rendered
  moveScence()
}
```

#### 1.4：游戏状态的变换

​	到目前为止，游戏的初始化已经完成了。接下来就是如何让游戏从初始化的状态转变成游戏中的状态。

- 这里采用`touchBegin`的方法来监听鼠标的点击以便于切换游戏的状态

```swift
override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        switch gameStatus {
        case .idle: startGame()
        case .running: print("给一个向上的力")
        case .over: shuffle()
        }
    }
```

#### 1.5：切换游戏状态

​	通过鼠标的点击来切换到游戏中的状态后

- 此时我们需要加入障碍物，也就是柱子。

    那么添加柱子的时候需要定位到柱子的位置，这个位置需要满足的条件

    1：位置随机

    2：高度不能等于整个屏幕的高度

    3：同一位置上不能同时出现两个柱子

```swift
func createRandomPipes() {
  let height = self.size.height - self.floor1.size.height
  // arc4random_uniform(_ __upper_bound: UInt32) -> UInt32 这个能产生随机数的最大值，也就是限定了一个范围
  let pipeGap = CGFloat(arc4random_uniform(UInt32(bird.size.height))) + bird.size.height * 2.5
  let pipeWidth = CGFloat(60.0)
  let topPipeHeight = CGFloat(arc4random_uniform(UInt32(height - pipeGap)))
  let bottomPipeHeight = height - pipeGap - topPipeHeight
	// 确定位置之后，调用添加障碍物的方法
  addPipes(topSize: CGSize(width: pipeWidth, height: topPipeHeight), bottomSize: CGSize(width: pipeWidth, height: bottomPipeHeight))
}
```

- 添加障碍物

```swift
func addPipes(topSize: CGSize, bottomSize: CGSize) {
  let topTexture = SKTexture(imageNamed: "topPipe")
  let topPipe = SKSpriteNode(texture: topTexture, size: topSize)
  topPipe.name = "pipe"
  topPipe.position = CGPoint(x: self.size.width + topPipe.size.width * 0.5, y: self.size.height - topPipe.size.height * 0.5)

  let bottomTexture = SKTexture(imageNamed: "bottomPipe")
  let bottomPipe = SKSpriteNode(texture: bottomTexture, size: bottomSize)
  bottomPipe.name = "pipe"
  bottomPipe.position = CGPoint(x: self.size.width + bottomPipe.size.width * 0.5, y: self.floor1.size.height + bottomPipe.size.height * 0.5)

  addChild(topPipe)
  addChild(bottomPipe)
}
```

- 我们要重复创建障碍物

```swift
func startCreateRandomPipesAction() {
  //创建一个等待的action,等待时间的平均值为3.5秒，变化范围为1秒
  let waitAct = SKAction.wait(forDuration: 3.5, withRange: 1.0)
  //创建一个产生随机水管的action，这个action实际上就是调用一下我们上面新添加的那个createRandomPipes()方法
  let generatePipeAct = SKAction.run {
    self.createRandomPipes()
  }
  //并且给这个循环的动作设置了一个叫做"createPipe"的key来标识它
  run(SKAction.repeatForever(SKAction.sequence([waitAct, generatePipeAct])), withKey: "createPipe")

}
```

- 障碍物添加完成之后需要让障碍物随着屏幕的左移动来不断生成障碍物

    这时候我们需要给障碍物添加动作行为

    因为柱子的移动是随着地面的移动来进行的

    所以应该吧柱子的移动的方法添加到`moveScene`中

    ```swift
    for pipeNode in self.children where pipeNode.name == "pipe" {
      //因为我们要用到水管的size，但是SKNode没有size属性，所以我们要把它转成SKSpriteNode
      if let pipeSprite = pipeNode as? SKSpriteNode {
        //将水管左移1
        pipeSprite.position = CGPoint(x: pipeSprite.position.x - 1, y: pipeSprite.position.y)
        //检查水管是否完全超出屏幕左侧了，如果是则将它从场景里移除掉
        if pipeSprite.position.x < -pipeSprite.size.width * 0.5 {
          pipeSprite.removeFromParent()
        }
      }
    }
    ```

- 既然有障碍物的添加，那必须也需要障碍物的移出

```swift
func stopCreateRandomPipesAction() {
  self.removeAction(forKey: "createPipe")
}
```

- 当游戏接受的时候需要移除掉界面中的所有障碍物

    这里需要移除所有障碍物

```swift
func removeAllPipesNode() {
  for pipe in self.children where pipe.name == "pipe" {
    //循环检查场景的子节点，同时这个子节点的名字要为pipe
    pipe.removeFromParent() 
  }
}
```

### 2：物理世界

#### 2.1：场景

```swift
// 在didMove方法中添加以下代码s
self.physicsBody = SKPhysicsBody(edgeLoopFrom: self.frame)
self.physicsWorld.contactDelegate = self
```

#### 2.2：物理体的准备

```swift
// 需要在GameScence外加入下面内容
let birdCategory: UInt32 = 0x1 << 0
let pipeCategory: UInt32 = 0x1 << 1
let floorCategory: UInt32 = 0x1 << 2
```

#### 2.3：地面物理体

```swift
// 地面一
floor1.physicsBody = SKPhysicsBody(edgeLoopFrom: CGRect(x: 0, y: 0, width: floor1.size.width, height: floor1.size.height))
floor1.physicsBody?.categoryBitMask = floorCategory
// 地面二
floor2.physicsBody = SKPhysicsBody(edgeLoopFrom: CGRect(x: 0, y: 0, width: floor2.size.width, height: floor2.size.height))
floor2.physicsBody?.categoryBitMask = floorCategory
```

#### 2.4：小鸟物理体

```swift
//在didMove()方法中，添加小鸟与初始化游戏之前添加以下内容
bird.physicsBody = SKPhysicsBody(texture: bird.texture!, size: bird.size)
bird.physicsBody?.allowsRotation = false  //禁止旋转
bird.physicsBody?.categoryBitMask = birdCategory //设置小鸟物理体标示
//设置可以小鸟碰撞检测的物理体
bird.physicsBody?.contactTestBitMask = floorCategory | pipeCategory 
```

#### 2.4：水管物理体

```swift
//在addPipes(topSize: CGSize, bottomSize: CGSize)方法，在addChild(topPipe),addChild(bottomPipe)代码之前加入下面内容
topPipe.physicsBody = SKPhysicsBody(texture: topTexture, size: topSize)
topPipe.physicsBody?.isDynamic = false
topPipe.physicsBody?.categoryBitMask = pipeCategory
//配置下水管物理体
bottomPipe.physicsBody = SKPhysicsBody(texture: bottomTexture, size: bottomSize)
bottomPipe.physicsBody?.isDynamic = false
bottomPipe.physicsBody?.categoryBitMask = pipeCategory
```

#### 2.5：小鸟的重力世界

​		开始游戏小鸟才受重力影响

```swift
//在shuffle()方法里，设置小鸟的position的代码后面加上下面这句
bird.physicsBody?.isDynamic = false
//然后再在startGame()方法里，开始创建水管代码之前加上下面这句
bird.physicsBody?.isDynamic = true
```

#### 2.6：小鸟的速度

```swift
//找到touchesBegan()方法,将print("给小鸟一个向上的力")换成以下内容
// 每次点击给小鸟一个力
bird.physicsBody?.applyImpulse(CGVector(dx: 0, dy: 20))
```

#### 2.7：检测碰撞

```swift
func didBegin(_ contact: SKPhysicsContact) {
    //先检查游戏状态是否在运行中，如果不在运行中则不做操作，直接return
    if gameStatus != .running { return }
    //为了方便我们判断碰撞的bodyA和bodyB的categoryBitMask哪个小，小的则将它保存到新建的变量bodyA里的，大的则保存到新建变量bodyB里
    var bodyA : SKPhysicsBody
    var bodyB : SKPhysicsBody
    if contact.bodyA.categoryBitMask < contact.bodyB.categoryBitMask {
        bodyA = contact.bodyA
        bodyB = contact.bodyB
    }else {
        bodyA = contact.bodyB
        bodyB = contact.bodyA
    }
    if (bodyA.categoryBitMask == birdCategory && bodyB.categoryBitMask == pipeCategory) || (bodyA.categoryBitMask == birdCategory && bodyB.categoryBitMask == floorCategory) {
        gameOver()
    }
}
```

#### 2.8：结束游戏

```swift
// 添加接受游戏提示label
lazy var gameOverLabel: SKLabelNode = {
    let label = SKLabelNode(fontNamed: "Chalkduster")
    label.text = "Game Over"
    return label
}()
```

#### 2.9：结束之后

```swift
//禁止用户点击屏幕
isUserInteractionEnabled = false
//添加gameOverLabel到场景里
addChild(gameOverLabel)
//设置gameOverLabel其实位置在屏幕顶部
gameOverLabel.position = CGPoint(x: self.size.width * 0.5, y: self.size.height)
//让gameOverLabel通过一个动画action移动到屏幕中间
gameOverLabel.run(SKAction.move(by: CGVector(dx:0, dy:-self.size.height * 0.5), duration: 0.5), completion: {
    //动画结束才重新允许用户点击屏幕
    self.isUserInteractionEnabled = true
})
```

> 另外，找到`shuffle()`方法，添加以下内容
>
> `gameOverLabel.removeFromParent()`