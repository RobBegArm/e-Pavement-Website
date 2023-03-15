const Spinner = (props) => {
  return (
    <img
      key={`placeholderFor:${props.imgName}`}
      alt={props.imgAlt}
      src={`${process.env.PUBLIC_URL}/assets/images/util/spinner.gif`}
      style={{
        backgroundColor: "rgba(0, 0, 0, 0.1)",
        width: "260px",
        height: "260px",
        margin: "auto auto",
      }}
    />
  );
};

export default Spinner;
